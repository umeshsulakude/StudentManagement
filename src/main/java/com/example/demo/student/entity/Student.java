package com.example.demo.student.entity;

import com.example.demo.department.entity.Department;

import javax.persistence.*;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return
                age == student.age &&
                        Objects.equals(id, student.id) &&
                        Objects.equals(name, student.name) &&
                        Objects.equals(gender, student.gender) &&
                        Objects.equals(department, student.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, department);
    }



//    @Override
//    public String toString() {
//        return "Student{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", age=" + age +
//                ", gender='" + gender + '\'' +
//                ", department=" + department + // Assuming department is a field in Student entity
//                '}';
//    }

    @Override
    public String toString() {
        StringBuilder ss = new StringBuilder();
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("age", age)
                .append("gender", gender)
                .append("department", department) // Assuming department is a field in Student entity
                .toString();
    }


}
