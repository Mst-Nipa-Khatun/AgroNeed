package com.nipa.agroneed.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.utils.ResponseBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


import java.io.IOException;

@Configuration
//learn us how to unauthorised access can be handled.
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); //when unauthorised access can be access then we customs the error log.thats why we use this logger class,here we inject this class

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        Response failResponse = ResponseBuilder.getFailResponse(HttpStatus.UNAUTHORIZED, null,
                "Detected unauthorized access to this resource.");

        // Set response properties
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Write JSON to response
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), failResponse);

        logger.error("\n\nURL: {}\nError Message: {}\nCustom Message:{} ",
                request.getRequestURI(), e.getMessage(), mapper.writeValueAsString(failResponse));

    }
}
