package com.example.PortfolioBackend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {


    private String title;
    private String location;
    private String description;
    private MultipartFile multipartFile;

}
