package com.example.PortfolioBackend.Services;

import com.example.PortfolioBackend.DTOs.PhotoIncoming;
import com.example.PortfolioBackend.Exceptions.PrimaryKeyTakenException;
import com.example.PortfolioBackend.Models.Photo;
import com.example.PortfolioBackend.Repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
     * @param photoIncoming - which contains the bytes and title of the photo.
     * Should save the bytes to drive at photoStorageLocation, identifying them with photoTitle.
     * */
    public void savePhotoToDrive(PhotoIncoming photoIncoming) throws IOException {
        //byte[] byteData = photoIncoming.getBytes();
        savePhotoToDrive(photoIncoming.getTitle(),photoIncoming.getBytes());
    }

    public void savePhotoInformationInDB(Photo photo) {
        photoRepository.save(photo);
    }

    /**
     * @throws PrimaryKeyTakenException if photo title is used in database.
     * Saves photo in the set storage location.
     * Saves photo information to database.
     * */
    public void storeIncomingPhoto(PhotoIncoming photoIncoming) throws PrimaryKeyTakenException, IOException {
        if (photoRepository.existsById(photoIncoming.getTitle())) {
            throw new PrimaryKeyTakenException(photoIncoming.getTitle());
        }

        /* Maintain this order so that if there is an IO issue
            the exception is thrown before info is saved to database. */
        savePhotoToDrive(photoIncoming);
        photoRepository.save(new Photo(photoIncoming));
    }
}