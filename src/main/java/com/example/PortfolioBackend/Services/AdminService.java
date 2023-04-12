package com.example.PortfolioBackend.Services;

import com.example.PortfolioBackend.DTOs.AdminCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    public AdminService(){

    }

    public boolean validAdminCredentials(AdminCredentials credentials) {
        return adminUsername.equals(credentials.getUsername()) && adminPassword.equals(credentials.getPassword());
    }

}
