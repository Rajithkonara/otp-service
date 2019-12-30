package com.tokoin.otp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokoin.otp.dto.request.OtpRequestDto;
import com.tokoin.otp.service.OtpService;
import com.tokoin.otp.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests the  {@link OtpController}
 */
public class OtpControllerTest {

    @Mock
    private OtpService otpService;
    private Validator validator = new Validator();
    private OtpRequestDto otpRequestDto = emailOtpRequest();

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);
        OtpController otpController = new OtpController(otpService, validator);
        mockMvc = MockMvcBuilders.standaloneSetup(otpController).build();
    }

    @Test
    void Should_ReturnOk_When_GenerateOppIsSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                 .post("/api/v1/otp/generate")
                .header("app-key", "xxxx")
                .content(asJsonString(otpRequestDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private OtpRequestDto emailOtpRequest() {
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setType("email");
        otpRequestDto.setEmail("randy@tokoin.io");
        return otpRequestDto;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
