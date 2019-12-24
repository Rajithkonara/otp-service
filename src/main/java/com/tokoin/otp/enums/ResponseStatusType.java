package com.tokoin.otp.enums;

public enum ResponseStatusType {

    SUCCESS("Success"),
    ERROR("Error"),
    UNAUTHORISED("Unauthorised"),
    FORBIDDEN("Forbidden"),
    SUCCESSFULLY_SENT("SUCCESSFULLY_SENT");

    private String status;

    private ResponseStatusType(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }

}
