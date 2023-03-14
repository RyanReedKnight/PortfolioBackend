package com.example.PortfolioBackend.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Photo {

    // ID to serve as the photo's unique identifier.
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    Integer photoID;

    // Location photo was taken.
    @Column(name="location")
    String location;

    

}
