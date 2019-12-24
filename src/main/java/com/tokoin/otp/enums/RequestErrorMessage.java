package com.tokoin.otp.enums;

public enum RequestErrorMessage {

    INVALID_MOBILE_NO("Invalid mobile number provided"),
    INVALID_EMAIL("Invalid email provided"),
    ERROR_CODE_INVALID_MOBILE("4000"),
    MOBILE_NO_NOT_PROVIDED("Mandatory field mobile no not provided"),
    EMAIL_NOT_PROVIDED("Mandatory field email not provided"),
    INVALID_TYPE("Invalid type provided"),
    INTERNAL_SERVER_ERROR_CODE("50001");

    private String errorMessage;

    private RequestErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}
