package com.example.demo.department.service;

import com.example.demo.department.entity.Department;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/department")
public class DepartmentUIController {

    private String departmentApiUrl = "http://localhost:8080/departments";


    @GetMapping("/")
    public String getWelcomPage(Model model) {
        return "helloDepartment";
    }
    @GetMapping("/getdepartment")
    public String getDepartmentByName(@PathVariable String deptName) {
        String apiUrl = departmentApiUrl + "/departments/byname/" + deptName;
        RestTemplate restTemplate = new RestTemplate();
        Department department = restTemplate.getForObject(apiUrl, Department.class);
        // Handle department object as needed
        return "your-view"; // Return your view name
    }

    @PostMapping("/add")
    public String saveDepartment(@RequestBody Department department) {
        String apiUrl = departmentApiUrl + "/departments/save";
        RestTemplate restTemplate = new RestTemplate();
        Department savedDepartment = restTemplate.postForObject(apiUrl, department, Department.class);
        // Handle savedDepartment object as needed
        return "your-view"; // Return your view name
    }

    // Other methods...
}

