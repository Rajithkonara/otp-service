package com.tokoin.otp.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter @Setter
public class OtpVerifyRequestDto implements Serializable {

    @NotBlank(message = "Type cannot be Null")
    private String type;
    private String mobileNo;
    private String email;
    @NotBlank(message = "OTP cannot be Null")
    private String otp;

    public boolean isRequiredMobileNo() {
        return isNonEmpty(mobileNo);
    }

    public boolean isRequiredEmail() { return isNonEmpty(email); }

    public boolean isRequiredOtp() {
        return isNonEmpty(otp);
    }

    protected boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }

}
