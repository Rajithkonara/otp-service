package com.tokoin.otp.dto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @deprecated  remove after testing completed
 *
 */
@Deprecated(forRemoval = true)
public class SMS {

    private static final String DEFAULT_ALGORITHM = "SHA1PRNG";

    public static Integer generateOtp() {
        int rand = 0;
        try {
            Random random = SecureRandom.getInstance(DEFAULT_ALGORITHM);
            rand = 100000 + random.nextInt(900000);
            // 100000 <= n <= 999999
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        return rand;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 200; i++) {
           Integer  x  =generateOtp();
            System.out.println(String.valueOf(x).length());
        }


    }

}
