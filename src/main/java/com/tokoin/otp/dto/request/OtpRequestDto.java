package com.tokoin.otp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsOtpRequestDto {

    private String type;
    private String mobileNo;
    private String email;

}
