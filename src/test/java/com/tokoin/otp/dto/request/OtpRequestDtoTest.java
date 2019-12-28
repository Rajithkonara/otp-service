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

public class OtpRequestDtoTest {

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

    OtpRequestDto otpRequestDtoEmail = getMockOtpRequestDto();

    @Test
    void Should_ReturnTrue_When_AllFieldsAreValidated() {

        Set<ConstraintViolation<OtpRequestDto>> violations
                = validator.validate(otpRequestDtoEmail);

        assertTrue(violations.isEmpty());
    }

    @Test
    void Should_ReturnFalse_When_emailIsUnavailable() {
        otpRequestDtoEmail.setEmail("");
        assertFalse(otpRequestDtoEmail.isRequiredEmail());
    }

    @Test
    void Should_ReturnFalse_When_mobileNoIsUnavailable() {
        otpRequestDtoEmail.setMobileNo("");
        assertFalse(otpRequestDtoEmail.isRequiredMobileNo());
    }

    private OtpRequestDto getMockOtpRequestDto() {
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setType("email");
        otpRequestDto.setEmail("rajithk@tokoin.io");
        return otpRequestDto;
    }

}
