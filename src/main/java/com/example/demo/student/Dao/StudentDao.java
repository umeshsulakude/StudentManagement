package com.example.demo.student.Dao;

import com.example.demo.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends JpaRepository<Student, Long>  {

    @Query("SELECT s FROM Student s WHERE s.name = :name AND s.age = :age")
    List<Student> getByName(@Param("name") String name, @Param("age") int age);

}
