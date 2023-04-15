package com.example.PortfolioBackend.Models;

import com.example.PortfolioBackend.DTOs.Photo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "photos")
public class PhotoRecord {

    // ID to serve as the photo's unique identifier.
    @Id
    @Column(name = "title", nullable = false, updatable = false)
    private String title;
    // Location photo was taken.
    @Column(name="location")
    private String location;

    // Description of photo.
    @Column(name="description")
    private String description;

    public PhotoRecord(Photo photo) {
        // ID to be defined by database
        this.title = photo.getTitle();
        this.location = photo.getLocation();
        this.description = photo.getDescription();
    }

}
