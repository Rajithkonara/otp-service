package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.enums.ResponseStatusType;
import com.tokoin.otp.exception.OtpCacheException;
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

    private OtpSMSCacheRepository cacheRepository;

    @Autowired
    public OtpServiceImpl(OtpSMSCacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    //should this be synchronous ?
    @Override
    public int generateSMSOtp() {
        int rand = 0;
        try {
            Random random = SecureRandom.getInstance(DEFAULT_ALGORITHM);
            rand = random.nextInt(1000000);
        } catch (NoSuchAlgorithmException e) {
            log.error("Invalid algorithm", e);
        }
        return rand;
    }

    @Override
    public OtpRequestDto sendOtpSms(OtpRequestDto otpRequestDto) {
        int otp = generateSMSOtp();
        log.info("Otp ---> {}", otp);
        cacheRepository.put(otpRequestDto.getMobileNo(), otp);
        return otpRequestDto;
    }


    @Override
    public OtpRequestDto sendEmailOtp(OtpRequestDto otpRequestDto) {
        int otp = generateSMSOtp();
        log.info("Otp ---> {}", otp);
        cacheRepository.put(otpRequestDto.getEmail(), otp);
        return otpRequestDto;
    }

    @Override
    public boolean verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto) {

        Optional<String> cachedOtp = cacheRepository.get(otpVerifyRequestDto.getMobileNo());

        if (cachedOtp.isPresent() && cachedOtp.get().equals(otpVerifyRequestDto.getOtp())) {
            log.info("OTP found Removing the OTP {} ", cachedOtp);
            cacheRepository.remove(otpVerifyRequestDto.getMobileNo());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public ResponseEntity<Object> verifySsmOtp(OtpVerifyRequestDto otpVerifyRequestDto) {

        Optional<String> cachedOtp = cacheRepository.get(otpVerifyRequestDto.getMobileNo());

        if (cachedOtp.isPresent() && cachedOtp.get().equals(otpVerifyRequestDto.getOtp())) {
            log.info("OTP found Removing the OTP {} ", cachedOtp);
            cacheRepository.remove(otpVerifyRequestDto.getMobileNo());

            ResponseWrapper responseWrapper =
                    new ResponseWrapper(ResponseStatusType.SUCCESS, "SUCCESSFULLY_SENT", null);
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
    public boolean verifyEmailOtp(OtpVerifyRequestDto otpVerifyRequestDto) {

        Optional<String> cachedOtp = cacheRepository.get(otpVerifyRequestDto.getEmail());

        if (cachedOtp.isPresent() && cachedOtp.get().equals(otpVerifyRequestDto.getOtp())) {
            log.info("OTP found Removing the OTP {} ", cachedOtp);
            cacheRepository.remove(otpVerifyRequestDto.getEmail());
            return true;
        } else {
            return false;
        }

    }

}
