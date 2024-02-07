package com.example.demo.config;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    public CustomJwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // JWT Token is in the form "Bearer token". Remove Bearer word and
            // get  only the Token
            String jwtToken = extractJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)){
                UserDetails userDetails = new User(jwtUtil.getUsernameFromToken(jwtToken),"",jwtUtil.getRolesFromToken(jwtToken));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else{
                System.out.println("Cannot set the security context");
            }
        } catch (ExpiredJwtException | BadCredentialsException ex){
            request.setAttribute("exception", ex);
            throw ex;
        }

        filterChain.doFilter(request,response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }


}
