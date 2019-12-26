package com.tokoin.otp.controller;

import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.enums.ResponseStatusType;
import com.tokoin.otp.exception.OtpCacheException;
import com.tokoin.otp.service.OtpService;
import com.tokoin.otp.util.Validator;
import com.tokoin.otp.wrapper.ErrorResponseWrapper;
import com.tokoin.otp.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/otp")
public class OtpController {

    private static final String INVALID_MOBILE_NO = "Invalid mobile number provided";
    private static final String INVALID_EMAIL = "Invalid email provided";
    private static final String MOBILE_NO_NOT_PROVIDED = "Mandatory field mobile no not provided";
    private static final String EMAIL_NOT_PROVIDED = "Mandatory field email not provided";
    private static final String ERROR_CODE_INVALID_MOBILE = "4000";
    private static final String TYPE_MOBILE = "mobile";
    private static final String TYPE_EMAIL = "email";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String INVALID_TYPE = "Invalid type provided";

    private OtpService otpService;
    private final Validator validator;

    @Autowired
    public OtpController(OtpService otpService, Validator validator) {
        this.otpService = otpService;
        this.validator = validator;
    }

    @PostMapping("/generate")
    public ResponseEntity<Object> generateOtp(@Valid @RequestBody OtpRequestDto otpRequestDto) {

        try {
            if (otpRequestDto.getType().equals(TYPE_MOBILE)) {
                return generateSmsResponseEntity(otpRequestDto);
            } else if (otpRequestDto.getType().equals(TYPE_EMAIL)) {
                return generateEmailResponseEntity(otpRequestDto);
            } else {
                ResponseWrapper responseWrapper =
                        new ResponseWrapper(ResponseStatusType.ERROR, INVALID_TYPE, null);
                return new ResponseEntity<>(responseWrapper, HttpStatus.NOT_FOUND);
            }

        } catch (OtpCacheException e) {
            log.error("OTP generation failed " + e);
            ResponseWrapper responseWrapper =
                    new ResponseWrapper(ResponseStatusType.ERROR, INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/verify")
    public ResponseEntity<Object> verifyOtp(@Valid @RequestBody OtpVerifyRequestDto otpVerifyRequestDto) {

        try {
            if (otpVerifyRequestDto.getType().equals(TYPE_MOBILE)) {
                return smsVerifyResponseEntity(otpVerifyRequestDto);
            } else if (otpVerifyRequestDto.getType().equals(TYPE_EMAIL)) {
                return emailVerifyResponseEntity(otpVerifyRequestDto);
            } else {
                ResponseWrapper responseWrapper =
                        new ResponseWrapper(ResponseStatusType.ERROR, INVALID_TYPE, null);
                return new ResponseEntity<>(responseWrapper, HttpStatus.NOT_FOUND);
            }
        } catch (OtpCacheException e) {
            log.error("OTP Validation failed " + e);
            ResponseWrapper responseWrapper =
                    new ResponseWrapper(ResponseStatusType.ERROR, INTERNAL_SERVER_ERROR, null);
            return ResponseEntity.badRequest().body(responseWrapper);
        }

    }

    /**
     * Save the email and otp to cache and send email
     *
     * @param otpRequestDto otp request parameters
     * @return ResponseEntity
     */
    private ResponseEntity<Object> generateEmailResponseEntity(@RequestBody @Valid OtpRequestDto otpRequestDto) {
        if (otpRequestDto.isRequiredEmail()) {
            if (validator.isValidEmail(otpRequestDto.getEmail())) {
                //generate otp and save to cache
               return otpService.sendEmailOtp(otpRequestDto);
            } else {
                return invalidRequestError(INVALID_EMAIL, ERROR_CODE_INVALID_MOBILE);
            }
        } else {
            return invalidRequestError(EMAIL_NOT_PROVIDED, ERROR_CODE_INVALID_MOBILE);
        }
    }

    /**
     * Save the mobileNo to cache and send sms
     * @param otpRequestDto otp request parameters
     * @return ResponseEntity
     */
    private ResponseEntity<Object> generateSmsResponseEntity(@RequestBody @Valid OtpRequestDto otpRequestDto) {
        if (otpRequestDto.isRequiredMobileNo()) {
            if (validator.isValidMobileNo(otpRequestDto.getMobileNo())) {
                //generate otp and save to cache
                return otpService.sendOtpSms(otpRequestDto);
            } else {
                return invalidRequestError(INVALID_MOBILE_NO, ERROR_CODE_INVALID_MOBILE);
            }
        } else {
            return invalidRequestError(MOBILE_NO_NOT_PROVIDED, ERROR_CODE_INVALID_MOBILE);
        }
    }


    /**
     * Return the error response for invalid request
     *
     * @param errorMsg error message
     * @param errorCode error code
     * @return errorResponseWrapper
     */
    private ResponseEntity<Object> invalidRequestError(String errorMsg, String errorCode) {
        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper(errorCode,
                ResponseStatusType.ERROR, errorMsg, null);
        return ResponseEntity.badRequest().body(errorResponseWrapper);
    }

    /**
     * Read the cache and get the saved otp for email
     * @param otpVerifyRequestDto otpVerifyRequestDto
     * @return ResponseEntity
     */
    private ResponseEntity<Object> emailVerifyResponseEntity(@RequestBody @Valid
                                                                     OtpVerifyRequestDto otpVerifyRequestDto) {

        if (otpVerifyRequestDto.isRequiredEmail()) {
            if (validator.isValidEmail(otpVerifyRequestDto.getEmail())) {
                return otpService.verifyEmailOtp(otpVerifyRequestDto);
            } else {
                return invalidRequestError(INVALID_EMAIL, ERROR_CODE_INVALID_MOBILE);
            }
        } else {
            return invalidRequestError("Required Fields are missing ", ERROR_CODE_INVALID_MOBILE);
        }
    }

    /**
     * Read the cache and get the saved otp for sms
     * @param otpVerifyRequestDto otpVerifyRequestDto
     * @return ResponseEntity
     */
    private ResponseEntity<Object> smsVerifyResponseEntity(@RequestBody @Valid
                                                                   OtpVerifyRequestDto otpVerifyRequestDto) {

        if (otpVerifyRequestDto.isRequiredMobileNo()) {
            if (validator.isValidMobileNo(otpVerifyRequestDto.getMobileNo())) {
                //verify sms otp
                return otpService.verifySmsOtp(otpVerifyRequestDto);
            } else {
                return invalidRequestError(INVALID_MOBILE_NO, ERROR_CODE_INVALID_MOBILE);
            }
        } else {
            return invalidRequestError("Required Fields are missing ", ERROR_CODE_INVALID_MOBILE);
        }
    }

}
