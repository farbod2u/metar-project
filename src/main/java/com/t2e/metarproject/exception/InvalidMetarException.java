package com.t2e.metarproject.exception;

public class InvalidMetarException extends RuntimeException {
    public InvalidMetarException() {
        super("Invalid METAR data");
    }
}
