package com.example.PortfolioBackend.Exceptions;

public class BadCredentialsException extends Exception {

    public BadCredentialsException() {
        super("Invalid credentials");
    }

}
