package com.example.PortfolioBackend.DTOs;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoIncoming {


    private String title;
    private String location;
    private String description;
    private byte[] bytes;

}
