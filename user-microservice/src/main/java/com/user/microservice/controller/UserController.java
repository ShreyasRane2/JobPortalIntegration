package com.user.microservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.microservice.entity.User;
import com.user.microservice.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController() {
    }

    @GetMapping({"/profile/{id}"})
    public ResponseEntity<User> findUserId(@PathVariable Long id) throws Exception {
        User user = this.userService.findByUserId(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            if (user == null) {
                return new ResponseEntity<>("User object is null", HttpStatus.BAD_REQUEST);
            } else {
                this.userService.createUser(user);
                return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return new ResponseEntity<>("Error creating user: " + var3.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping({""})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = this.userService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception var4) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean isDeleted = this.userService.deleteUser(id);
        return isDeleted ? new ResponseEntity<>("User deleted successfully!", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
