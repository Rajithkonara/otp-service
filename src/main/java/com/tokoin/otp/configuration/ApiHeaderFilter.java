package com.tokoin.otp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class ApiHeaderFilter extends GenericFilterBean {

    @Value("${spring.application.app-key}")
    private String applicationKey;
    private static final String APPLICATION_HEADER = "app-key";

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
                throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            HttpServletResponse response = (HttpServletResponse)servletResponse;

            String appKey = request.getHeader(APPLICATION_HEADER);

            if (appKey == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Mandatory Header");
            }
            if (appKey != null && !appKey.equals(applicationKey)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed to use this service");
                return;
            }

            filterChain.doFilter(servletRequest,servletResponse);
        }
}
