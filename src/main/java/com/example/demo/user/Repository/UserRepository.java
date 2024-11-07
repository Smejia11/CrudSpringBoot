package com.example.demo.user.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.user.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}