package com.example.demo.user.Service;

import org.springframework.stereotype.Service;

import com.example.demo.user.Dto.LoginReq;
import com.example.demo.user.Dto.LoginRes;
import com.example.demo.user.Dto.UserDto;
import com.example.demo.user.Dto.UserViewDto;
import com.example.demo.user.Model.User;
import com.example.demo.user.Repository.UserRepository;
import com.example.demo.user.Utils.PasswordUtil;
import com.example.demo.user.Utils.JwtUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Transactional
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Transactional
	public UserViewDto updateUser(Long userId, UserDto requestUser) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			userRepository.save(requestUser.build(optionalUser.get()));
			return new UserViewDto(optionalUser.get());
		}
		throw new EntityNotFoundException("User with id " + userId + " not found");
	}

	@Transactional
	public UserViewDto deleteUser(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			userRepository.deleteById(userId);
			return new UserViewDto(optionalUser.get());
		}
		throw new EntityNotFoundException("User with id " + userId + " not found");
	}

	@Transactional
	public UserViewDto getUserId(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			return new UserViewDto(optionalUser.get());
		}
		throw new EntityNotFoundException("User with id " + userId + " not found");
	}

	public LoginRes authEmailUser(LoginReq loginRaw) throws Exception {
		Optional<User> optionalUser = userRepository.findByEmail(loginRaw.getEmail());
		if (!optionalUser.isPresent()) {
			return null;
		}
		boolean isValidPassword = PasswordUtil.verifyPassword(loginRaw.getPassword(), optionalUser.get().getPassword());
		if (!isValidPassword) {
			return null;
		}
		String token = jwtUtil.createToken(optionalUser.get());
		String email = loginRaw.getEmail();

		return new LoginRes(token, email);

	}

}
