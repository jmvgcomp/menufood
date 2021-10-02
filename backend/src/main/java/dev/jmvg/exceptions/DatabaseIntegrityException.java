package dev.jmvg.exceptions;

public class DatabaseIntegrityException extends RuntimeException{

    public DatabaseIntegrityException(String message) {
        super(message);
    }
}
