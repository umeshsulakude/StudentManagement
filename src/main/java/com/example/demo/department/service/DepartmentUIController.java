package com.example.demo.department.service;

import com.example.demo.department.entity.Department;
import com.example.demo.jsonmodels.UserLoginModel;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/department")
public class DepartmentUIController {

    private String departmentApiUrl = "http://localhost:8080/departments";


    @GetMapping("/")
    public String getWelcomPage() {
        return "helloDepartment";
    }

    @GetMapping("/delete")
    public String deleteDepartment(Model model, HttpServletRequest httpRequest) {
        getDepartments(model,httpRequest);
        return "deleteDepartment";
    }

    @GetMapping("/getdepartments")
    public String getDepartments(Model model, HttpServletRequest httpRequest) {

        String securityToken = (String) httpRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", securityToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

//        RestTemplate restTemplate = new RestTemplate();
//        Department[] departments = restTemplate.getForObject()

        ResponseEntity<Department[]> response = new RestTemplate().exchange(
                departmentApiUrl + "/getAllDepartments",
                HttpMethod.GET,
                httpEntity,
                Department[].class
        );


        if (response.getStatusCode().is2xxSuccessful()) {
            // Extract the departments from the response body
            Department[] departments = response.getBody();
            List<Department> departmentList = (departments != null) ? Arrays.asList(departments) : new ArrayList<>();

            // Add the departments to the model
            model.addAttribute("departments", departmentList);
        } else {
            // Handle the case where the request was not successful
            // You might want to log an error or handle it in another way
            System.err.println("Failed to fetch departments. Status code: " + response.getStatusCodeValue());
        }
        return "department";
    }

    @GetMapping("/add")
    public String addDepartmentPage(Model model) {
        return "addDepartment";
    }

    @PostMapping("/addDepartment")
    public String addDepartment(Department department, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Department[] departments = restTemplate.getForObject(departmentApiUrl + "/getAllDepartments", Department[].class);

        boolean departmentExists = Arrays.stream(Objects.requireNonNull(departments))
                .anyMatch(s -> s.getDeptName().equals(department.getDeptName()));

        if (departmentExists) {
            model.addAttribute("errorMessage", "Department already exists!"); // Add error message to model
            return "addDepartment"; // Return to the addDepartment page with error message
        } else {
            URI uri = restTemplate.postForLocation(departmentApiUrl + "/save", department);
            return "redirect:/department/getdepartments";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(departmentApiUrl + "/deletedepartment", id);
        return "redirect:/department/getdepartments";
    }
    @PostMapping("/deleteByIds")
    public String deleteDepartments(@RequestParam("departmentIds") List<Long> Ids) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = restTemplate.postForLocation(departmentApiUrl + "/deletedepartments", Ids);
        return "redirect:/department/delete";
    }
}

