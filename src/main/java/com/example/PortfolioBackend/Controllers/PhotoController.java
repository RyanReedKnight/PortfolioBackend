package com.example.PortfolioBackend.Controllers;

import com.example.PortfolioBackend.DTOs.Photo;
import com.example.PortfolioBackend.Exceptions.PrimaryKeyTakenException;
import com.example.PortfolioBackend.Services.AdminService;
import com.example.PortfolioBackend.Services.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            System.out.println("UNAUTHORIZED REQUEST MADE");
            resp.setStatus(401);
            return "YOUR NOT AUTHORIZED TO POST PHOTOS!";
        }

        Photo photo = new Photo(title,location,description,multipartFile);

        try {
            photoService.storeIncomingPhoto(photo);
        } catch (PrimaryKeyTakenException e) {
            System.out.println("BAD PRIMARY KEY " + photo.getTitle() + " is already being used.");
            resp.setStatus(201);
            return photo.getTitle() + " is already in use.";
        } catch (IOException e) {
            System.out.println("IO EXCEPTION");
            resp.setStatus(500);
            return "Bad server";
        }

        System.out.println(photo + " successfully saved.");
        resp.setStatus(201);
        return "Success";
    }

}
