package com.example.PortfolioBackend.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdminCredentials {
    private String username;
    private String password;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AdminCredentials adminCredentials)) {
            return false;
        } else {
            return username.equals(adminCredentials.username) && password.equals(adminCredentials.password);
        }
    }
}
