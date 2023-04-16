package com.example.PortfolioBackend.Services;

import com.example.PortfolioBackend.DTOs.Photo;
import com.example.PortfolioBackend.Exceptions.PrimaryKeyTakenException;
import com.example.PortfolioBackend.Exceptions.RecordDoesNotExistException;
import com.example.PortfolioBackend.Models.PhotoRecord;
import com.example.PortfolioBackend.Repositories.PhotoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PhotoServiceTestSuite {

    @Value("${storage.photos}")
    private String testingStorage;
    MultipartFile multipartFile;
    String photoTitle = "unique-title";

    @Autowired
    PhotoService photoService;
    @Autowired
    PhotoRepository photoRepository;

    @BeforeAll
    void setUp() throws IOException {
        multipartFile = new MockMultipartFile(photoTitle,
                new byte[]{0,0,1,1,1,1,1,1,1,1,});
    }

    @AfterAll
    void cleanUp() {
        try {
            photoService.deletePhoto(photoTitle);
        } catch (RecordDoesNotExistException e) {
            System.out.println("Nothing to clean.");
        }
    }

    @Test
    void testPhotoServiceSavesFileToDrive() {
        Assertions.assertDoesNotThrow(() -> photoService.savePhotoToDrive(photoTitle,
                multipartFile.getBytes()), String.valueOf(IOException.class));
        Assertions.assertTrue(photoService.deleteImageFileFromDrive(photoTitle));
    }

    /**
     * For state to be consistent, the photo title must 1) have a file in the drive name after it,
     * 2) have a corresponding record in the database, with it as the records primary key
     * */
    @Test
    void testDatabaseAndDriveInConsistentState_givenPhotoStorageServiceCallsStoreIncomingPhoto() throws
            PrimaryKeyTakenException, IOException {
        Photo photo = new Photo(photoTitle,
                "The lost city of Atlantis", "description...",multipartFile);
        photoService.storeIncomingPhoto(photo);
        Path filePath = Paths.get(testingStorage, photoTitle);

        Assertions.assertTrue(Files.exists(filePath) && photoRepository.existsById(photoTitle));
    }

    /**
     * For state to be consistent 1) record associated with photoTitle must be removed from database,
     * and 2) file associated with photoTitle must be removed.
     * */
    @Test
    void testDataBaseAndDriveInConsistentState_givenPhotoStorageServiceCallsDeletePhoto() throws IOException,
            RecordDoesNotExistException {
        Photo photo = new Photo(photoTitle,
                "The lost city of Atlantis", "description...",multipartFile);
        PhotoRecord photoRecord = new PhotoRecord(photo);
        // Save to database
        photoRepository.save(photoRecord);
        // Save photo to drive
        File file = new File(testingStorage+ "/" + photoTitle);
        multipartFile.transferTo(file);

        // Confirm record and file have been successfully created manually.
        Path filePath = Paths.get(testingStorage, photoTitle);
        Assertions.assertTrue(Files.exists(filePath) && photoRepository.existsById(photoTitle));

        photoService.deletePhoto(photoTitle);

        // Confirm file has been removed and the record has been removed
        Assertions.assertTrue(!Files.exists(filePath) && !photoRepository.existsById(photoTitle));
    }

}
