package com.tokoin.otp.enums;

public enum ResponseErrorMessage {

    INTERNAL_SERVER_ERROR("Internal Server Error");

    private String errorMessage;

    private ResponseErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
