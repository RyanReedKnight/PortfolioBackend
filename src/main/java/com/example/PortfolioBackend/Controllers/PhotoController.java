package com.example.PortfolioBackend.Controllers;

import com.example.PortfolioBackend.DTOs.Photo;
import com.example.PortfolioBackend.Exceptions.PrimaryKeyTakenException;
import com.example.PortfolioBackend.Exceptions.RecordDoesNotExistException;
import com.example.PortfolioBackend.Models.PhotoRecord;
import com.example.PortfolioBackend.Services.AdminService;
import com.example.PortfolioBackend.Services.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/photos")
public class PhotoController {



    private final AdminService adminService;
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService, AdminService adminService) {
        this.photoService = photoService;
        this.adminService = adminService;
    }


    @PostMapping
    public String postPhoto(@RequestParam("photoFile") MultipartFile multipartFile, @RequestParam("title") String title,
                                    @RequestParam("location") String location, @RequestParam String description,
                                    HttpServletResponse resp) {

        String token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getHeader("Authorization");

        // Confirm request is authorized. DO NOT REMOVE THIS
        // OR REARRANGE ANYTHING THAT COMES AFTER IT WITH RESPECT TO IT !!!
        if (!adminService.authorizedRequest(token)) {
            System.out.println("com.example.PortfolioBackend.Controllers.PhotoController.postPhoto" +
                    " \"UNAUTHORIZED REQUEST MADE\"");
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return "YOUR NOT AUTHORIZED TO POST PHOTOS!";
        }

        Photo photo = new Photo(title,location,description,multipartFile);

        try {
            photoService.storeIncomingPhoto(photo);
        } catch (PrimaryKeyTakenException e) {
            System.out.println("com.example.PortfolioBackend.Controllers.PhotoController.postPhoto " +
                    "\"BAD PRIMARY KEY " + photo.getTitle() + " is already being used.\"");
            resp.setStatus(HttpStatus.ALREADY_REPORTED.value());
            return photo.getTitle() + " is already in use.";
        } catch (IOException e) {
            System.out.println("com.example.PortfolioBackend.Controllers.PhotoController.postPhoto \"IO EXCEPTION\"");
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "Bad server";
        }

        System.out.println("com.example.PortfolioBackend.Controllers.PhotoController.postPhoto \"" +
                photo.getTitle() + " successfully saved.\"");
        resp.setStatus(HttpStatus.OK.value());
        return "Success";
    }

    @DeleteMapping("/delete/{photo-title}")
    String deletePhoto(@PathVariable("photo-title") String photoTitle, HttpServletResponse resp) {
        String token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getHeader("Authorization");

        // Confirm request is authorized. DO NOT REMOVE THIS
        // OR REARRANGE ANYTHING THAT COMES AFTER IT WITH RESPECT TO IT !!!
        if (!adminService.authorizedRequest(token)) {
            System.out.println("com.example.PortfolioBackend.Controllers.PhotoController.deletePhoto" +
                    " \"UNAUTHORIZED REQUEST MADE\"");
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return "YOUR NOT AUTHORIZED TO POST PHOTOS!";
        }

        System.out.println("ATTEMPTING TO DELETE " + photoTitle);

        try {
            photoService.deletePhoto(photoTitle);
            return photoTitle + " SUCCESSFULLY DELETED.";
        } catch (RecordDoesNotExistException e) {
            return "ATTEMPTED TO DELETE NON-EXISTENT PHOTO.";
        }
    }

    @GetMapping("/records")
    List<PhotoRecord> getPhotoRecords(HttpServletResponse resp) {
        System.out.println("FETCHED PHOTO RECORDS");
        return photoService.fetchPhotoRecords();
    }
}