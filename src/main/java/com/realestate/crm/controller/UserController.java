package com.realestate.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.crm.enumerations.UserRole;
import com.realestate.crm.model.User;
import com.realestate.crm.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8100")
public class UserController {
	
	@Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/city/{city}")
    public List<User> getUsersByCity(@PathVariable String city) {
        return userService.getUsersByCity(city);
    }

    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable UserRole role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/manager/{managerId}/team")
    public List<User> getFullTeam(@PathVariable Long managerId) {
        User manager = userService.getAllUsers()
                                  .stream()
                                  .filter(u -> u.getId().equals(managerId))
                                  .findFirst()
                                  .orElseThrow();
        return userService.getTeamHierarchy(manager);
    }
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
