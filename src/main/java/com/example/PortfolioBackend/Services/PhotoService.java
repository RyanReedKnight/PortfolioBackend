package com.example.PortfolioBackend.Services;

import com.example.PortfolioBackend.DTOs.PhotoIncoming;
import com.example.PortfolioBackend.Models.Photo;
import com.example.PortfolioBackend.Repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PhotoService {
    PhotoRepository photoRepository;
    @Value("${storage.photos}")
    private String storageLocation;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    /**
     * As of now the method should have two outcomes, 1) the photo's information should be saved in
     * the database, and 2) the photo itself should be saved to a drive.
     * TODO: Improve this setup once it is better understood.
     * TODO: Should check for existing photo and create unique title if one of the same title already exists.
     * */
    public void savePhotoToDrive(MultipartFile image/*, Photo photoInfo*/) throws IOException {
        // Save photo information to database.
        /*photoRepository.save(photoInfo);*/
        // Save photo to a specified drive.
        Path photoPath = Paths.get(storageLocation + "/444" /*photoInfo.getTitle()*/);
        Files.write(photoPath, image.getBytes());
    }

}
