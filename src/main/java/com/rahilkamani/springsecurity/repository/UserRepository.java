package com.rahilkamani.springsecurity.repository;

import com.rahilkamani.springsecurity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByuserName(String userName);
}
