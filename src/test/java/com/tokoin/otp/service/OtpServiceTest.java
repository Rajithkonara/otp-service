package com.tokoin.otp.service;

import com.tokoin.otp.exception.OtpCacheException;
import com.tokoin.otp.repository.OtpSMSCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * This class tests for the {@link OtpService}
 */
public class OtpServiceTest {

    private OtpServiceImpl otpService;
    @Mock
    private OtpSMSCacheRepository otpSMSCacheRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        otpService = new OtpServiceImpl(otpSMSCacheRepository);
    }

    @Test
    void Should_ThrowException_When_generatingOtpFailed() {
        OtpServiceImpl mock = org.mockito.Mockito.mock(OtpServiceImpl.class);
        when(mock.generateOtp()).thenThrow(new OtpCacheException("Failed to generate Otp "));
        OtpCacheException exception = assertThrows(OtpCacheException.class, mock::generateOtp);
        assertEquals("Failed to generate Otp ", exception.getMessage());

    }

    @Test
    void Should_Generate_Otp() {

        int otp = otpService.generateOtp();
        int count = 0;

        while (otp != 0) {
            otp /= 10;
            ++count;
        }
        assertEquals(6, count);

    }

    @Test
    void Should_ThrowException_When_SavingOtpToCacheFailed() {
        doThrow(new OtpCacheException("Error while saving to cache ")).when(otpSMSCacheRepository).put("key", 1234);
        OtpCacheException exception = assertThrows(OtpCacheException.class, () ->
                otpSMSCacheRepository.put("key", 1234));
        assertEquals("Error while saving to cache ", exception.getMessage());
    }

    @Test
    void Should_SaveOtpToCache() {
        doNothing().when(spy(otpSMSCacheRepository)).put("key", 1234);
    }


    @Test
    void Should_ThrowException_When_GetOtpToCacheFailed() {

        doThrow(new OtpCacheException("Error while retrieving from the cache ")).when(otpSMSCacheRepository).get("key");

        OtpCacheException exception = assertThrows(OtpCacheException.class, () ->
                otpSMSCacheRepository.get("key"));
        assertEquals("Error while retrieving from the cache ", exception.getMessage());
    }


    @Test
    void Should_ThrowException_When_DeleteOtpToCacheFailed() {

        doThrow(new OtpCacheException("Error while removing from the cache ")).when(otpSMSCacheRepository).
                remove("key");

        OtpCacheException exception = assertThrows(OtpCacheException.class, () ->
                otpSMSCacheRepository.remove("key"));
        assertEquals("Error while removing from the cache ", exception.getMessage());

    }


}
