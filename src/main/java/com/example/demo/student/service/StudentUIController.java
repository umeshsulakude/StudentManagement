package com.example.demo.student.service;

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
@RequestMapping("/student")
public class StudentUIController {

    private final String apiUrl = "http://localhost:8080/students"; // Replace with your API URL


    @GetMapping("/")
    public String getWelcomPage(Model model) {
        return "hello";
    }

    @GetMapping("/getstudents")
    public String getAllStudents(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Student[] students = restTemplate.getForObject(apiUrl + "/getAllStudents", Student[].class);
        List<Student> studentList = Arrays.asList(students);
        model.addAttribute("students", studentList);
        ;
        return "Student";
    }

    @GetMapping("/add")
    public String AddStudentPage(Model model) {
        return "addStudent";
    }

    @GetMapping("/delete")
    public String DeleteStudents(Model model) {
        getAllStudents(model);
        return "deleteStudent";
    }

    @PostMapping("/addStudent")
    public String addStudent(Student student, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Student[] students = restTemplate.getForObject(apiUrl + "/getAllStudents", Student[].class);
        boolean studentExists = Arrays.stream(Objects.requireNonNull(students))
                .anyMatch(s -> s.getName().equals(student.getName()) && s.getAge() == student.getAge());

        if (studentExists) {
            model.addAttribute("errorMessage", "Student already exists!"); // Add error message to model
            return "addStudent"; // Return to the addStudent page with error message
        } else {
            URI uri = restTemplate.postForLocation(apiUrl + "/addStudents", student);
            return "redirect:/student/getstudents";
        }
    }

    @PostMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(apiUrl + "/deletestudent", id);
        return "redirect:/student/getstudents";
    }

    @PostMapping("/deleteByIds")
    public String deleteStudents(@RequestParam("studentIds") List<Long> Ids) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = restTemplate.postForLocation(apiUrl + "/deletestudents", Ids);
        return "redirect:/student/delete";
    }
}
