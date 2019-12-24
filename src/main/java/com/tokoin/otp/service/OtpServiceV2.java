package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import org.springframework.http.ResponseEntity;

public interface OtpServiceV2 {

    ResponseEntity<Object> verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto);

}
