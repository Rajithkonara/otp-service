package com.tokoin.otp.controller;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.enums.ResponseStatusType;
import com.tokoin.otp.repository.OtpSMSCacheRepository;
import com.tokoin.otp.service.OtpService;
import com.tokoin.otp.wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    private OtpService otpService;

    @Autowired
    private OtpSMSCacheRepository repository;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ResponseWrapper> generateOtp(@RequestBody OtpRequestDto otpRequestDto) {

        //check the type
        if (otpRequestDto.getType().equals("mobile")) {

            // call the generate otp
            int integer = otpService.generateSMSOtp();
            System.out.println(integer);
            repository.put(otpRequestDto.getMobileNo(), integer);

//            Integer integer1 = repository.get(otpRequestDto.getMobileNo());
////
//            System.out.println(integer1);
            //save key value in redis cache

        }


        ResponseWrapper responseWrapper =
                new ResponseWrapper(ResponseStatusType.SUCCESS, "SUCCESSFULLY_SENT", null);

        return ResponseEntity.ok().body(responseWrapper);
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseWrapper> verifyOtp(@RequestBody OtpVerifyRequestDto otpVerifyRequestDto) {

//        Integer msisdn = repository.get(otpVerifyRequestDto.getMobileNo());
//
//        if (msisdn.toString().equals(otpVerifyRequestDto.getOtp())) {
//            System.out.println("equals.....");
//        }
//
//        ResponseWrapper responseWrapper =
//                new ResponseWrapper(ResponseStatusType.SUCCESS, "OTP : "+ msisdn, null);

        return ResponseEntity.ok().build();
    }


}
