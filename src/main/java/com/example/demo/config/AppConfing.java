package com.example.demo.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.user.Model.User;
import com.example.demo.user.Repository.UserRepository;

@Configuration
public class AppConfing {

	@Autowired
	private UserRepository userRepository;

	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			Optional<User> user = userRepository.findByEmail(username);
			if (!user.isPresent()) {
				throw new UsernameNotFoundException("User not found");
			}
			return org.springframework.security.core.userdetails.User.builder().username(user.get().getEmail())
					.password(user.get().getPassword()).build();
		};
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
}
