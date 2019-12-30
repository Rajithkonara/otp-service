package com.tokoin.otp.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the  {@link Validator}
 */
public class ValidatorTest {

    private static final String EMAIL = "rendy@tokoin.io";
    private static final String MOBILE_NO = "+6281234567890";
    private Validator validator = new Validator();

    @Test
    void Should_ReturnTrue_When_EmailIsValid() {
        assertTrue(validator.isValidEmail(EMAIL));
    }

    @Test
    void Should_ReturnTrue_When_MobileNoIsValid() {
        assertTrue(validator.isValidMobileNo(MOBILE_NO));
    }

}
