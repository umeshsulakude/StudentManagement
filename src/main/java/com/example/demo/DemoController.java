package com.example.demo;

import com.example.demo.student.entity.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class DemoController {

    @GetMapping("/")
    public String getWelcomPage(Model model) {
        return "WelcomePage";
    }
}

