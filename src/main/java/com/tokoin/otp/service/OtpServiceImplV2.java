package com.tokoin.otp.service;

import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.enums.ResponseStatusType;
import com.tokoin.otp.repository.OtpSMSCacheRepository;
import com.tokoin.otp.util.Validator;
import com.tokoin.otp.wrapper.ErrorResponseWrapper;
import com.tokoin.otp.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OtpServiceImplV2 implements OtpServiceV2 {

    private static final String INVALID_MOBILE_NO = "Invalid mobile number provided";
    private static final String ERROR_CODE_INVALID_MOBILE = "4000";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String SUCCESSFULLY_SENT = "SUCCESSFULLY_SENT";
    public static final String INTERNAL_SERVER_ERROR_CODE = "50001";

    private final Validator validator;
    private final OtpSMSCacheRepository cacheRepository;

    @Autowired
    public OtpServiceImplV2(Validator validator, OtpSMSCacheRepository cacheRepository) {
        this.validator = validator;
        this.cacheRepository = cacheRepository;
    }

    @Override
    public ResponseEntity<Object> verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto) {

        if (otpVerifyRequestDto.isRequiredMobileNo() && otpVerifyRequestDto.isRequiredOtp()) {

            if (validator.isValidMobileNo(otpVerifyRequestDto.getMobileNo())) {

                Optional<String> mobileNo = cacheRepository.get(otpVerifyRequestDto.getMobileNo());

                if (mobileNo.isPresent() && mobileNo.get().equals(otpVerifyRequestDto.getOtp())) {
                    log.info("OTP found Removing the OTP {} ", mobileNo);
                    cacheRepository.remove(otpVerifyRequestDto.getMobileNo());
                    ResponseWrapper responseWrapper =
                            new ResponseWrapper(ResponseStatusType.SUCCESS, SUCCESSFULLY_SENT, null);
                    return ResponseEntity.ok().body(responseWrapper);

                } else {
                    return internalServerError("Otp not valid", INTERNAL_SERVER_ERROR_CODE);
                }

            } else {
                return invalidRequestError(INVALID_MOBILE_NO, ERROR_CODE_INVALID_MOBILE);
            }
        } else {
            return invalidRequestError("Required Fields are missing ", ERROR_CODE_INVALID_MOBILE);
        }
    }


    private ResponseEntity<Object> internalServerError(String errorMsg, String errorCode) {
        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper(errorCode,
                ResponseStatusType.ERROR, errorMsg, null);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponseWrapper);
    }


    /**
     * Return the error response for invalid request
     *
     * @param errorMsg
     * @param errorCode
     * @return
     */
    private ResponseEntity<Object> invalidRequestError(String errorMsg, String errorCode) {
        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper(errorCode,
                ResponseStatusType.ERROR, errorMsg, null);
        return ResponseEntity.badRequest().body(errorResponseWrapper);
    }


}
