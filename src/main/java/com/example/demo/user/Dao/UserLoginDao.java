package com.example.demo.user.Dao;

import com.example.demo.student.entity.Student;
import com.example.demo.user.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLoginDao extends JpaRepository<UserLogin, Integer> {

    @Query("SELECT u FROM UserLogin u WHERE u.username = :username")
    UserLogin getByName(@Param("username") String username);

}
