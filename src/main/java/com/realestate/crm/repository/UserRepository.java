package com.realestate.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.crm.enumerations.UserRole;
import com.realestate.crm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByCity(String city);
    List<User> findByRole(UserRole role);
    List<User> findByManager(User manager);
    User findByEmail(String email);

}
