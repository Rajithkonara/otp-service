package com.tokoin.otp.controller;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.exception.OtpCacheException;
import com.tokoin.otp.service.OtpService;
import com.tokoin.otp.service.OtpServiceV2;
import com.tokoin.otp.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    private static final String TYPE_MOBILE = "mobile";
    private static final String TYPE_EMAIL = "email";

    private OtpServiceV2 otpService;

    @Autowired
    public OtpController(OtpServiceV2 otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ResponseWrapper> generateOtp(@RequestBody OtpRequestDto otpRequestDto) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseWrapper> verifyOtp(@RequestBody OtpVerifyRequestDto otpVerifyRequestDto) {

        try {

            if (otpVerifyRequestDto.getType().equals(TYPE_MOBILE)) {
                otpService.verifySmsOtp(otpVerifyRequestDto);
            } else if (otpVerifyRequestDto.getType().equals(TYPE_EMAIL)) {

            }

        } catch (OtpCacheException e) {
            log.error("Error occurred while verifying the OTP  {}" ,
                    e);
        }
        return ResponseEntity.ok().build();
    }


}
