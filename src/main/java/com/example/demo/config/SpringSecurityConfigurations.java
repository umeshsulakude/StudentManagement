package com.example.demo.config;

import com.example.demo.user.service.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurations extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private UserController userDetailsService;

    @Autowired
    private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public SpringSecurityConfiguration(UserController UserController, CustomJwtAuthenticationFilter customJwtAuthenticationFilter, JwtAuthenticationEntryPoint unauthorizedHandler) {
//        this.userDetailsService = UserController;
//        this.customJwtAuthenticationFilter = customJwtAuthenticationFilter;
//        this.unauthorizedHandler = unauthorizedHandler;
//    }




    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/departments", "/departments/", "/department", "/department/", "/add", "/addDepartment", "/delete", "/getdepartments", "/departments/getdepartments", "/deleteByIds", "/delete/**").hasRole("ADMIN")
                .antMatchers("/students", "/students/**", "/student", "/student/**" ).hasAnyRole("USER", "ADMIN")
                .antMatchers("/authenticate", "/register", "/login", "/", "/addUser", "/user/getAllUsers", "/getAllUsers", "/user/addUser", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("JSESSIONID") // Set the logout URL
                .permitAll();
    }

}
