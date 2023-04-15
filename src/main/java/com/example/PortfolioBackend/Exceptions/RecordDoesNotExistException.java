package com.example.PortfolioBackend.Exceptions;

public class RecordDoesNotExistException extends Exception {

    public RecordDoesNotExistException(String primaryKey) {
        super("Attempt to access non-existent record. " +
                "No record associated with " + primaryKey);
    }

    public RecordDoesNotExistException() {
        super("Attempt to access non-existent record.");
    }

}
