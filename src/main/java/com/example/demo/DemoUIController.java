package com.example.demo;

import com.example.demo.config.JwtUtil;
import com.example.demo.config.LoginController;
import com.example.demo.jsonmodels.AuthenticationResponse;
import com.example.demo.jsonmodels.UserLoginModel;
import com.example.demo.user.entity.UserLogin;
import com.example.demo.user.service.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class DemoUIController {

    private AuthenticationManager authenticationManager;
    private UserController userDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    private LoginController LoginController;

    public DemoUIController(AuthenticationManager authenticationManager, UserController userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public String getWelcomPage(Model model) {
        return "WelcomePage";
    }

    @PostMapping("/home")
    public String getDashBoardPage(Model model, @RequestBody  UserLoginModel userLoginModel) throws ServletException, IOException {
        LoginController.manaualDoFilter(userLoginModel.getToken());
      //  SecurityContextHolder.getContext().setAuthentication(userLoginModel.getToken());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "Login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        return "Register";
    }


    private String userApiUrl = "http://localhost:8080/user";

    @PostMapping("/addUser")
    public String addUser(UserLogin userLogin, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        UserLogin[] users = restTemplate.getForObject(userApiUrl + "/getAllUsers", UserLogin[].class);
        boolean usersExists = Arrays.stream(Objects.requireNonNull(users))
                .anyMatch(s -> s.getUsername().equals(userLogin.getUsername()));

        if (usersExists) {
            model.addAttribute("errorMessage", "User already exists!"); // Add error message to model
            return "Register"; // Return to the addStudent page with error message
        } else {
            URI uri = restTemplate.postForLocation(userApiUrl + "/addUser", userLogin);
            return "redirect:/login";
        }
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginModel authenticationRequest) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}

