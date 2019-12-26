package com.tokoin.otp.exception;

/**
 * OtpCache Exception
 */
public class OtpCacheException extends RuntimeException {

    /**
     * OtpCache Exception with error message
     * @param errorMessage error message
     */
    public OtpCacheException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * OtpCache Exception with error message and throwable
     * @param errorMessage error message
     * @param throwable error
     */
    public OtpCacheException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

}
