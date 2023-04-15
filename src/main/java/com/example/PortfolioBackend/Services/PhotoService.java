package com.example.PortfolioBackend.Services;


import com.example.PortfolioBackend.DTOs.MultipartFileImpl;
import com.example.PortfolioBackend.DTOs.Photo;
import com.example.PortfolioBackend.Exceptions.PrimaryKeyTakenException;
import com.example.PortfolioBackend.Exceptions.RecordDoesNotExistException;
import com.example.PortfolioBackend.Models.PhotoRecord;
import com.example.PortfolioBackend.Repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType;

@Service
public class PhotoService {

    PhotoRepository photoRepository;
    @Value("${storage.photos}")
    private String photoStorageLocation;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    /**
     * @param photoTitle - the title of the file being stored.
     * @param imageBytes - the bytes that comprise the image being stored.
     * Should save the bytes to drive at @photoStorageLocation, identifying them with @photoTitle.
     * */
    public void savePhotoToDrive(String photoTitle, byte[] imageBytes) throws IOException {
        // Save photo to a specified drive.
        Path photoPath = Paths.get(photoStorageLocation + "/" + photoTitle);
        Files.write(photoPath, imageBytes);
    }

    /**
     * @param photoTitle - the title of the file being stored.
     * @param multipartFile - multipart file associated with image being stored.
     * Should save the bytes to drive at @photoStorageLocation, identifying them with @photoTitle.
     * */
    public void savePhotoToDrive(String photoTitle, MultipartFile multipartFile) throws IOException {
        File file = new File(photoStorageLocation + "/" + photoTitle);
        multipartFile.transferTo(file);
    }

    /**
     * @param photo - which contains the bytes and title of the photo.
     * Should save the bytes to drive at photoStorageLocation, identifying them with photoTitle.
     * */
    public void savePhotoToDrive(Photo photo) throws IOException {
        //byte[] byteData = photoIncoming.getBytes();
        savePhotoToDrive(photo.getTitle(), photo.getMultipartFile());
    }

    public void savePhotoInformationInDB(PhotoRecord photo) {
        photoRepository.save(photo);
    }

    /**
     * @throws PrimaryKeyTakenException if photo title is used in database.
     * Saves photo in the set storage location.
     * Saves photo information to database.
     * */
    public void storeIncomingPhoto(Photo photo) throws PrimaryKeyTakenException, IOException {
        if (photoRepository.existsById(photo.getTitle())) {
            throw new PrimaryKeyTakenException(photo.getTitle());
        }

        /* Maintain this order so that if there is an IO issue
            the exception is thrown before info is saved to database. */
        savePhotoToDrive(photo);
        photoRepository.save(new PhotoRecord(photo));
    }

    public byte[] fetchPhotoBytesFromDrive(String photoTitle) throws RecordDoesNotExistException,
            IOException {

        if (!photoRepository.existsById(photoTitle)) {
            throw new RecordDoesNotExistException(photoTitle);
        }

        Path photoPath = Paths.get(photoStorageLocation + "/"
            + photoTitle);

        return Files.readAllBytes(photoPath);
    }



    /**
     * @throws RecordDoesNotExistException if the photo does not exist on the drive or in the database.
     * @param photoTitle - primary key associated with photo, both the drive and the record in the database
     * @return instance of Photo associated with title.
     * */
    public Photo fetchPhoto(String photoTitle) throws RecordDoesNotExistException,
            IOException {
        // If the record does not exist an exception is thrown here, this is why
        // I did not worry about a null pointer exception in the return statement.
        byte[] photoBytes = fetchPhotoBytesFromDrive(photoTitle);
        PhotoRecord photoRecord = photoRepository.findById(photoTitle).orElse(null);

        MultipartFileImpl photoFile = new MultipartFileImpl(new File(photoStorageLocation + "/" + photoTitle));

        return new Photo(photoRecord.getTitle(), photoRecord.getLocation(),
            photoRecord.getDescription(), photoFile);
    }

}