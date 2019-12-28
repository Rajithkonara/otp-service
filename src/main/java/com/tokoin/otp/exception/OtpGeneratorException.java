package com.tokoin.otp.exception;

public class OtpGeneratorException extends OtpCacheException {

    public OtpGeneratorException(String errorMessage) {
        super(errorMessage);
    }

    public OtpGeneratorException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
