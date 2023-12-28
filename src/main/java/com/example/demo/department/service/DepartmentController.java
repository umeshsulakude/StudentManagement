package com.example.demo.department.service;

import com.example.demo.department.Dao.DepartmentDao;
import com.example.demo.department.entity.Department;
import com.example.demo.student.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private final DepartmentDao departmentService;
    public DepartmentController(DepartmentDao departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/getAllDepartments")
    public List<Department> getAllDepartments() {
        return departmentService.findAll();
    }

    // Get department by name
    @GetMapping("/byName")
    public Department getDepartmentByName(@RequestParam String deptName) {
        Department department = departmentService.getByName(deptName);
        if (department != null) {
            return department;
        }
        return null;
    }

    // Save department
    @PostMapping("/save")
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.save(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

}

