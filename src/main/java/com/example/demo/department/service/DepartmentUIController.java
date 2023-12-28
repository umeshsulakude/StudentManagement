package com.example.demo.department.service;

import com.example.demo.department.entity.Department;
import com.example.demo.student.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/department")
public class DepartmentUIController {

    private String departmentApiUrl = "http://localhost:8080/departments";


    @GetMapping("/")
    public String getWelcomPage(Model model) {
        return "helloDepartment";
    }

    //    @GetMapping("/getdepartment")
//    public String getDepartmentByName(@PathVariable String deptName) {
//        String apiUrl = departmentApiUrl + "/byname/" + deptName;
//        RestTemplate restTemplate = new RestTemplate();
//        Department department = restTemplate.getForObject(apiUrl, Department.class);
//        // Handle department object as needed
//        return ""; // Return your view name
//    }

    @GetMapping("/getdepartments")
    public String getDepartments(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Department[] departments = restTemplate.getForObject(departmentApiUrl + "/getAllDepartments", Department[].class);
        List<Department> departmentList = Arrays.asList(departments);
        model.addAttribute("departments", departmentList);
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

}

