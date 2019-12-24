package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;

public interface OtpService {

    int generateSMSOtp();

    OtpRequestDto sendOtpSms(OtpRequestDto otpRequestDto);

    OtpRequestDto sendEmailOtp(OtpRequestDto otpRequestDto);

    boolean verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto);

    boolean verifyEmailOtp(OtpVerifyRequestDto otpVerifyRequestDto);

}
