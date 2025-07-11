package com.srm.trackportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srm.trackportal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByRegistrationNumber(String registrationNumber);
}
