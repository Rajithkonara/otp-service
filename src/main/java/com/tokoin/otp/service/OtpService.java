package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import org.springframework.http.ResponseEntity;

public interface OtpService {

    int generateOtp();

    ResponseEntity<Object> sendOtpSms(OtpRequestDto otpRequestDto);

    ResponseEntity<Object> sendEmailOtp(OtpRequestDto otpRequestDto);

    ResponseEntity<Object> verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto);

    ResponseEntity<Object> verifyEmailOtp(OtpVerifyRequestDto otpVerifyRequestDto);

}
