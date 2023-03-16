package com.example.PortfolioBackend.Models;

import com.example.PortfolioBackend.DTOs.PhotoIncoming;
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
public class Photo {

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

    public Photo(PhotoIncoming photoIncoming) {
        // ID to be defined by database
        this.title = photoIncoming.getTitle();
        this.location = photoIncoming.getLocation();
    }

}
