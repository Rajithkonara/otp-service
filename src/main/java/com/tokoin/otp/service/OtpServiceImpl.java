package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.repository.OtpSMSCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

        Optional<String> mobileNo =  cacheRepository.get(otpVerifyRequestDto.getMobileNo());

        if (mobileNo.isPresent() && mobileNo.get().equals(otpVerifyRequestDto.getOtp())) {
            log.info("OTP found Removing the OTP {} ", mobileNo);
            cacheRepository.remove(otpVerifyRequestDto.getMobileNo());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean verifyEmailOtp(OtpVerifyRequestDto otpVerifyRequestDto) {

        Optional<String> email = cacheRepository.get(otpVerifyRequestDto.getEmail());

        if (email.isPresent() && email.get().equals(otpVerifyRequestDto.getOtp())) {
            log.info("OTP found Removing the OTP {} ", email);
            cacheRepository.remove(otpVerifyRequestDto.getEmail());
            return true;
        } else {
            return false;
        }

    }

}
