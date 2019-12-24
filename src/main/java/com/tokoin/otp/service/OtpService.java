package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import org.springframework.http.ResponseEntity;

public interface OtpService {

    int generateSMSOtp();

    OtpRequestDto sendOtpSms(OtpRequestDto otpRequestDto);

    OtpRequestDto sendEmailOtp(OtpRequestDto otpRequestDto);

    boolean verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto);

    ResponseEntity<Object> verifySsmOtp(OtpVerifyRequestDto otpVerifyRequestDto);

    boolean verifyEmailOtp(OtpVerifyRequestDto otpVerifyRequestDto);

}
