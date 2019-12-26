package com.tokoin.otp.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter @Setter
public class OtpVerifyRequestDto implements Serializable {

    @NotBlank(message = "Type cannot be Null")
    private String type;
    private String mobileNo;
    private String email;
    @NotBlank(message = "OTP cannot be Null")
    @Pattern(regexp="^([0-9]{6})$", message = "Invalid otp provided")
    private String otp;

    public boolean isRequiredMobileNo() {
        return isNonEmpty(mobileNo);
    }

    public boolean isRequiredEmail() { return isNonEmpty(email); }

    protected boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }

}
