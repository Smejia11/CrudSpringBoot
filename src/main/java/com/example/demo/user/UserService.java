package com.example.demo.user;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Transactional
	public User updateUser(Long userId, DtoUser requestUser) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			return userRepository.save(requestUser.build(optionalUser.get()));
		}
		throw new EntityNotFoundException("User with id " + userId + " not found");
	}

	@Transactional
	public User deleteUser(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			userRepository.deleteById(userId);
			return optionalUser.get();
		}
		throw new EntityNotFoundException("User with id " + userId + " not found");
	}
	
	@Transactional
	public User getUserId(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		throw new EntityNotFoundException("User with id " + userId + " not found");
	}
	
	
}
