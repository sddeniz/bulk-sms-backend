package com.behsa.smsgw.common;

public class SmsGwException extends RuntimeException {
    public SmsGwException(String message) {
        super(message);
    }

    public SmsGwException(String message, Throwable cause) {
        super(message, cause);
    }
}
