package com.example.PortfolioBackend.Services;

import com.example.PortfolioBackend.DTOs.AdminCredentials;
import com.example.PortfolioBackend.Exceptions.BadTokenException;
import com.example.PortfolioBackend.Utilities.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    JWTUtility jwtUtility;

    @Autowired
    public AdminService(JWTUtility jwtUtility){
        this.jwtUtility = jwtUtility;
    }

    public boolean validAdminCredentials(AdminCredentials credentials) {
        return adminUsername.equals(credentials.getUsername()) && adminPassword.equals(credentials.getPassword());
    }

    public boolean authorizedRequest(String token) {
        try {
            System.out.println(jwtUtility.parseToken(token).orElse(""));
            return adminUsername.equals(jwtUtility.parseToken(token).orElse(""));
        } catch (BadTokenException e) {
            return false;
        }
    }

}
