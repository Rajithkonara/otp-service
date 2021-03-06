package com.tokoin.otp.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class OtpRequestDto implements Serializable {

    @NotBlank(message = "Type cannot be Null")
    private String type;
    private String mobileNo;
    private String email;

    public boolean isRequiredMobileNo() {
        return isNonEmpty(mobileNo);
    }

    public boolean isRequiredEmail() {
        return isNonEmpty(email);
    }

    protected boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }

}
