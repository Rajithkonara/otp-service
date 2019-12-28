package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.enums.ResponseStatusType;
import com.tokoin.otp.exception.OtpGeneratorException;
import com.tokoin.otp.repository.OtpSMSCacheRepository;
import com.tokoin.otp.wrapper.ErrorResponseWrapper;
import com.tokoin.otp.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    private static final String DEFAULT_ALGORITHM = "SHA1PRNG";
    public static final String SUCCESSFULLY_SENT = "Successfully sent OTP to user";
    public static final String SUCCESSFULLY_VERIFIED = "Successfully verified user";
    public static final String OTP_FOUND_REMOVING_THE_OTP = "OTP found Removing the OTP {} ";

    private OtpSMSCacheRepository cacheRepository;

    @Autowired
    public OtpServiceImpl(OtpSMSCacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    //should this be synchronous ?
    @Override
    public int generateOtp() {
        int rand = 0;
        try {
            Random random = SecureRandom.getInstance(DEFAULT_ALGORITHM);
            rand = 100000 + random.nextInt(900000);
            // 100000 <= n <= 999999
        } catch (NoSuchAlgorithmException e) {
            log.error("Invalid algorithm", e);
            throw new OtpGeneratorException("Failed to generate Otp ", e);
        }
        return rand;
    }

    @Override
    public ResponseEntity<Object> sendOtpSms(OtpRequestDto otpRequestDto) {
        int otp = generateOtp();
        log.info("Otp ---> {}", otp);
        cacheRepository.put(otpRequestDto.getMobileNo(), otp);

        // call to sms client and send back response accordingly

        ResponseWrapper responseWrapper =
                new ResponseWrapper(ResponseStatusType.SUCCESS, SUCCESSFULLY_SENT, null);
        return ResponseEntity.ok().body(responseWrapper);
    }


    @Override
    public ResponseEntity<Object> sendEmailOtp(OtpRequestDto otpRequestDto) {
        int otp = generateOtp();
        log.info("Otp ---> {}", otp);
        cacheRepository.put(otpRequestDto.getEmail(), otp);

        // call to email client and send back the response accordingly

        ResponseWrapper responseWrapper =
                new ResponseWrapper(ResponseStatusType.SUCCESS, SUCCESSFULLY_SENT, null);
        return ResponseEntity.ok().body(responseWrapper);
    }

    @Override
    public ResponseEntity<Object> verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto) {

        Optional<String> cachedOtp = cacheRepository.get(otpVerifyRequestDto.getMobileNo());

        if (cachedOtp.isPresent() && cachedOtp.get().equals(otpVerifyRequestDto.getOtp())) {
            log.info(OTP_FOUND_REMOVING_THE_OTP, cachedOtp.get());
            cacheRepository.remove(otpVerifyRequestDto.getMobileNo());

            ResponseWrapper responseWrapper =
                    new ResponseWrapper(ResponseStatusType.SUCCESS, SUCCESSFULLY_VERIFIED, null);
            return ResponseEntity.ok().body(responseWrapper);

        } else if (cachedOtp.isPresent() && !cachedOtp.get().equals(otpVerifyRequestDto.getOtp())) {
            ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper("4001",
                    ResponseStatusType.ERROR, "Otp expired", null);
            return ResponseEntity.badRequest().body(errorResponseWrapper);
        } else {
            ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper("4002",
                    ResponseStatusType.ERROR, "Invalid OTP", null);
            return ResponseEntity.badRequest().body(errorResponseWrapper);
        }

    }

    @Override
    public ResponseEntity<Object> verifyEmailOtp(OtpVerifyRequestDto otpVerifyRequestDto) {

        Optional<String> cachedEmail = cacheRepository.get(otpVerifyRequestDto.getEmail());

        if (cachedEmail.isPresent() && cachedEmail.get().equals(otpVerifyRequestDto.getOtp())) {
            log.info(OTP_FOUND_REMOVING_THE_OTP, cachedEmail.get());
            cacheRepository.remove(otpVerifyRequestDto.getEmail());

            ResponseWrapper responseWrapper =
                    new ResponseWrapper(ResponseStatusType.SUCCESS, SUCCESSFULLY_VERIFIED, null);
            return ResponseEntity.ok().body(responseWrapper);

        } else if (cachedEmail.isPresent() && !cachedEmail.get().equals(otpVerifyRequestDto.getOtp())) {
            ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper("4001",
                    ResponseStatusType.ERROR, "Otp expired", null);
            return ResponseEntity.badRequest().body(errorResponseWrapper);
        } else {
            ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper("4002",
                    ResponseStatusType.ERROR, "Invalid OTP", null);
            return ResponseEntity.badRequest().body(errorResponseWrapper);
        }
    }

}
