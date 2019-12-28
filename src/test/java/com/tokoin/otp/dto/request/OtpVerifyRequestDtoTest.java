package com.tokoin.otp.dto.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OtpVerifyRequestDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    OtpVerifyRequestDto otpVerifyRequestDto = otpVerifyRequestDto();

    @Test
    void Should_ReturnTrue_When_AllFieldsAreValidated() {

        Set<ConstraintViolation<OtpVerifyRequestDto>> violations
                = validator.validate(otpVerifyRequestDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void Should_ReturnFalse_When_emailIsUnavailable() {
        otpVerifyRequestDto.setEmail("");
        assertFalse(otpVerifyRequestDto.isRequiredEmail());
    }

    @Test
    void Should_ReturnFalse_When_mobileIsUnavailable() {
        otpVerifyRequestDto.setMobileNo("");
        assertFalse(otpVerifyRequestDto.isRequiredMobileNo());
    }

    private OtpVerifyRequestDto otpVerifyRequestDto() {
        OtpVerifyRequestDto otpVerifyRequestDto = new OtpVerifyRequestDto();
        otpVerifyRequestDto.setType("email");
        otpVerifyRequestDto.setMobileNo("+0812313213");
        otpVerifyRequestDto.setOtp("123456");
        return otpVerifyRequestDto;
    }
}