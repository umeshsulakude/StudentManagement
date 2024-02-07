package com.example.demo.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class LoginController {

    private JwtUtil jwtUtil;

    public LoginController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public void manaualDoFilter(String jwtToken) throws ServletException, IOException {
        try{
            // JWT Token is in the form "Bearer token". Remove Bearer word and
            // get  only the Token
            if(StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)){
                UserDetails userDetails = new User(jwtUtil.getUsernameFromToken(jwtToken),"",jwtUtil.getRolesFromToken(jwtToken));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else{
                System.out.println("Cannot set the security context : custom error");
            }
        } catch (ExpiredJwtException | BadCredentialsException ex){
            throw ex;
        }
    }
}
