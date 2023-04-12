package com.example.PortfolioBackend.Controllers;

import com.example.PortfolioBackend.DTOs.AdminCredentials;
import com.example.PortfolioBackend.Services.AdminService;
import com.example.PortfolioBackend.Utilities.JWTUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/admin")
public class AdminController {


    private final AdminService adminService;
    private final JWTUtility jwtUtility;

    @Autowired
    public AdminController(AdminService adminService, JWTUtility jwtUtility) {
        this.adminService = adminService;
        this.jwtUtility = jwtUtility;
    }

    /**
     * Returns token to client if given valid credentials.
     * */
    @PostMapping("/login")
    public String loginHandler(@RequestBody AdminCredentials adminCredentials, HttpServletResponse resp) {
        // If credentials are invalid, return a 401 error.
        if (adminService.validAdminCredentials(adminCredentials)) {
            String token = jwtUtility.createToken(adminCredentials.getUsername(), adminCredentials.getPassword());
            resp.setHeader("Authorization", token);
            resp.setStatus(201);
            return "Good credentials.";
        } else {
            resp.setStatus(401);
            return "Bad credentials";
        }
    }
}
