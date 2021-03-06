package com.tokoin.otp.util;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
public class DefaultOtpGenerator implements OtpGenerator {

    private static final String DEFAULT_ALGORITHM = "SHA1PRNG";

    /**
     * Generate Otp based on SHA1PRNG
     * @return otp
     */
    @Override
    public Integer generateOtp() {

        int rand = 0;
        try {
            Random random = SecureRandom.getInstance(DEFAULT_ALGORITHM);
            rand =  random.nextInt(1000000);
        } catch (NoSuchAlgorithmException e) {
            log.error("Invalid algorithm", e);
        }
        return rand;
    }

}
