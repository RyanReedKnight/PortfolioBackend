package com.example.PortfolioBackend.Controllers;

import com.example.PortfolioBackend.DTOs.PhotoIncoming;
import com.example.PortfolioBackend.Exceptions.BadTokenException;
import com.example.PortfolioBackend.Exceptions.PrimaryKeyTakenException;
import com.example.PortfolioBackend.Models.Photo;
import com.example.PortfolioBackend.Services.AdminService;
import com.example.PortfolioBackend.Services.PhotoService;
import com.example.PortfolioBackend.Utilities.JWTUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
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
    public String postPhoto(@RequestBody PhotoIncoming photoIncoming, HttpServletResponse resp) {

        String token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getHeader("Authorization");

        // Confirm request is authorized. DO NOT REMOVE THIS
        // OR REARRANGE ANYTHING THAT COMES AFTER IT WITH RESPECT TO IT !!!
        if (!adminService.authorizedRequest(token)) {
            System.out.println("UNAUTHORIZED REQUEST MADE");
            resp.setStatus(401);
            return "YOUR NOT AUTHORIZED TO POST PHOTOS!";
        }

        try {
            photoService.storeIncomingPhoto(photoIncoming);
        } catch (PrimaryKeyTakenException e) {
            System.out.println("BAD PRIMARY KEY " + photoIncoming.getTitle() + " is already being used.");
            resp.setStatus(201);
            return photoIncoming.getTitle() + " is already in use.";
        } catch (IOException e) {
            System.out.println("IO EXCEPTION");
            resp.setStatus(500);
            return "Bad server";
        }

        System.out.println(photoIncoming + " successfully saved.");
        resp.setStatus(201);
        return "Success";
    }

}
