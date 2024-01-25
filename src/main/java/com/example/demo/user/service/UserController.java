package com.example.demo.user.service;

import com.example.demo.jsonmodels.UserLoginModel;
import com.example.demo.user.Dao.UserLoginDao;
import com.example.demo.user.entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController implements UserDetailsService  {

    @Autowired
    private final UserLoginDao UserLoginDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public UserController(UserLoginDao userLoginDao, PasswordEncoder passwordEncoder) {

        this.UserLoginDao = userLoginDao;
        this.bcryptEncoder=passwordEncoder;
    }


    @GetMapping("/getAllUsers")
    public List<UserLogin> getAllUsers() {
        return UserLoginDao.findAll();
    }

    @GetMapping("/getByName")
    public UserLogin getByName(String userName) {
        return UserLoginDao.getByName(userName);
    }

    @PostMapping("/addUser")
    public UserLogin addUser(@RequestBody UserLoginModel userLoginModel) {
        UserLogin s = getByName(userLoginModel.getUsername());
        if (s== null || s.getUsername() == null) {
            UserLogin userLogin =buildUserLogin(userLoginModel);
            return UserLoginDao.save(userLogin);
        }
        return s;
        // Implement other CRUD operations (GET, PUT, DELETE) as needed
    }

    private  UserLogin buildUserLogin(UserLoginModel userLoginModel)
    {
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(userLoginModel.getUsername());
        userLogin.setPassword((bcryptEncoder.encode(userLoginModel.getPassword())));
        userLogin.setRole(userLoginModel.getRole());
        return  userLogin;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;
        UserLogin user = UserLoginDao.getByName(username);
        if(user != null){
            roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with username" + username);
    }

}

