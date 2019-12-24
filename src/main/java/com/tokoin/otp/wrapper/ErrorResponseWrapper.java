package com.tokoin.otp.wrapper;

import com.tokoin.otp.enums.ResponseStatusType;
import lombok.Getter;

@Getter
public class ErrorResponseWrapper {

    private ResponseStatusType status;
    private String message;
    private String data;
    private String errorCode;

    public ErrorResponseWrapper(String errorCode, ResponseStatusType statusType, String message, String data) {
        this.errorCode = errorCode;
        this.status = statusType;
        this.message = message;
        this.data = data;
    }

}
