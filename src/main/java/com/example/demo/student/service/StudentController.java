package com.example.demo.student.service;

import com.example.demo.student.entity.Student;
import org.aspectj.weaver.loadtime.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.student.Dao.StudentDao;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private final StudentDao studentRepository;

    public StudentController(StudentDao studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/getByName")
    public List<Student> getByName(Student student) {
        return studentRepository.getByName(student.getName(), student.getAge());
    }

    @PostMapping("/addStudents")
    public Student addStudent(@RequestBody Student student) {
 List<Student> s = getByName(student);
        if (s.isEmpty()) {
            return studentRepository.save(student);
        }
        return s.get(0);
        // Implement other CRUD operations (GET, PUT, DELETE) as needed
    }
    @PostMapping("/deletestudent")
    public  boolean deleteStudent(@RequestBody Long id)
    {
        studentRepository.deleteById(id);
        return true;
    }

    @PostMapping("/deletestudents")
    public  boolean deleteStudent(@RequestBody List<Long> Ids)
    {
        studentRepository.deleteAllById(Ids);
        return true;
    }
}

