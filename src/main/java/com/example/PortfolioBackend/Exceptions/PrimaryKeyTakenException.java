package com.example.PortfolioBackend.Exceptions;

public class PrimaryKeyTakenException extends Exception {
    public PrimaryKeyTakenException(String primaryKey) {
        super(primaryKey + " is already being used as a primary key.");
    }

    public PrimaryKeyTakenException() {
        super("Primary key taken.");
    }
}
