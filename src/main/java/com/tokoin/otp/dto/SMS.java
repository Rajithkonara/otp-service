package com.tokoin.otp.dto;

import lombok.SneakyThrows;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class SMS {

    private static final String DEFAULT_ALGORITHM = "SHA1PRNG";

    public static Integer generateOtp() {
        int rand = 0;
        try {
            Random random = SecureRandom.getInstance(DEFAULT_ALGORITHM);
            rand =  random.nextInt(1000000);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return rand;
    }

    public static void main(String[] args) {

        System.out.println(generateOtp());

    }

}
