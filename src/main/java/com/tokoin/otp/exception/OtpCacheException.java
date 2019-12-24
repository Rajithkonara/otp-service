package com.tokoin.otp.exception;

public class OtpCacheException extends RuntimeException {

    public OtpCacheException(String errorMessage) {
        super(errorMessage);
    }

    public OtpCacheException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

}
