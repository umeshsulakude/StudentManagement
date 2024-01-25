package com.example.demo.user.entity;

import javax.persistence.*;
import java.util.Objects;

import java.util.Objects;


@Entity
@Table(name = "user")
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UserLoginId;
    private String username;
    private String password;
    private String role;

    public Integer getUserLoginId() {
        return UserLoginId;
    }

    public void setUserLoginId(Integer userLoginId) {
        UserLoginId = userLoginId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(username, userLogin.username) &&
                Objects.equals(password, userLogin.password) &&
                Objects.equals(role, userLogin.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role);
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
