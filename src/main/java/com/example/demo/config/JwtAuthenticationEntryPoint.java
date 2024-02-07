package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message;
        // Check if the request has any exception
        final Exception exception = (Exception) request.getAttribute("exception");

        // If yes, then use to it to create response message else use the authException
        byte[] body = null;
        if(exception != null){
            body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("cause", exception.toString()));
        } else if(authException.getCause() != null){
            message = authException.getCause().toString();
            body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
        } else{
            message = authException.getMessage();
            body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
        }
        response.getOutputStream().write(body);
    }
}
