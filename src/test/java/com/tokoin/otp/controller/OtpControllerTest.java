package com.tokoin.otp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.dto.request.OtpVerifyRequestDto;
import com.tokoin.otp.service.OtpServiceImpl;
import com.tokoin.otp.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests the  {@link OtpController}
 */
@AutoConfigureMockMvc
public class OtpControllerTest {

    private static final String INVALID_TYPE = "Invalid type provided";
    private static final String OTP_GENERATE_URI = "/api/v1/otp/generate";
    private static final String OTP_VERIFY_URI = "/api/v1/otp/verify";

    @Mock
    private OtpServiceImpl otpService;
    private Validator validator = new Validator();
    private OtpRequestDto otpRequestDtoEmail = otpRequestDto();
    private OtpVerifyRequestDto otpVerifyRequestDto = otpVerifyRequestDto();

    @InjectMocks
    private OtpController otpController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        otpController = new OtpController(otpService, validator);
        this.mockMvc = MockMvcBuilders.standaloneSetup(otpController).build();
    }

    @Test
    void Should_ReturnOk_When_GenerateOtpIsSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post(OTP_GENERATE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .header("app-key", "xxxx")
                .content(asJsonString(otpRequestDtoEmail)))
                .andExpect(status().isOk());
    }

    @Test
    void Should_ReturnOk_When_VerifyOtpIsSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post(OTP_VERIFY_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .header("app-key", "xxxx")
                .content(asJsonString(otpVerifyRequestDto)))
                .andExpect(status().isOk());
    }


    @Test
    void Should_ReturnError_When_InvalidTypeIsProvidedForGenerateOtp() throws Exception {

        otpRequestDtoEmail.setType("text");

        mockMvc.perform(MockMvcRequestBuilders
                .post(OTP_GENERATE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .header("app-key", "xxxx")
                .content(asJsonString(otpRequestDtoEmail)))
                .andExpect(jsonPath("$.status", is("ERROR")))
                .andExpect(jsonPath("$.message", is(INVALID_TYPE)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void Should_ReturnError_When_InvalidTypeIsProvidedForVerifyOtp() throws Exception {

        otpVerifyRequestDto.setType("letter");

        mockMvc.perform(MockMvcRequestBuilders
                .post(OTP_VERIFY_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .header("app-key", "xxxx")
                .content(asJsonString(otpVerifyRequestDto)))
                .andExpect(jsonPath("$.status", is("ERROR")))
                .andExpect(jsonPath("$.message", is(INVALID_TYPE)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(status().is4xxClientError());
    }



    private OtpRequestDto otpRequestDto() {
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setType("email");
        otpRequestDto.setEmail("randy@tokoin.io");
        return otpRequestDto;
    }


    private OtpVerifyRequestDto otpVerifyRequestDto() {
        OtpVerifyRequestDto otpVerifyRequestDto = new OtpVerifyRequestDto();
        otpVerifyRequestDto.setType("mobile");
        otpVerifyRequestDto.setMobileNo("+6281123123");
        otpVerifyRequestDto.setOtp("123456");
        return otpVerifyRequestDto;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
