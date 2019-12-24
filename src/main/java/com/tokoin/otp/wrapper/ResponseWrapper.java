package com.tokoin.otp.wrapper;

import com.tokoin.otp.enums.ResponseStatusType;
import lombok.Getter;

@Getter
public class ResponseWrapper {

    private ResponseStatusType status;
    private String message;
    private String data;


    public ResponseWrapper(ResponseStatusType statusType, String message, String data) {
        this.status = statusType;
        this.message = message;
        this.data = data;
    }
}

