package com.example.PortfolioBackend.Exceptions;

public class BadTokenException extends Exception {

    public BadTokenException() {
        super("Empty or null token");
    }

}
