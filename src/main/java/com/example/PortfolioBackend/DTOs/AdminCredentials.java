package com.example.PortfolioBackend.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginCredentials {
    private String username;
    private String password;
}
