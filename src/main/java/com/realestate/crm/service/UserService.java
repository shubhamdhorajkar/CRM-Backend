package com.realestate.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.crm.enumerations.UserRole;
import com.realestate.crm.model.User;
import com.realestate.crm.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByCity(String city) {
        return userRepository.findByCity(city);
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public List<User> getTeamUnder(User manager) {
        return userRepository.findByManager(manager);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getTeamHierarchy(User rootUser) {
        List<User> result = new ArrayList<>();
        buildTeamHierarchy(rootUser, result);
        return result;
    }

    private void buildTeamHierarchy(User manager, List<User> result) {
        List<User> directReports = userRepository.findByManager(manager);
        for (User user : directReports) {
            result.add(user);
            buildTeamHierarchy(user, result);
        }
    }
    
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        existingUser.setDesignation(userDetails.getDesignation());
        existingUser.setRole(userDetails.getRole());
        existingUser.setCity(userDetails.getCity());
        existingUser.setRegion(userDetails.getRegion());
        existingUser.setActive(userDetails.isActive());
        existingUser.setManager(userDetails.getManager());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
