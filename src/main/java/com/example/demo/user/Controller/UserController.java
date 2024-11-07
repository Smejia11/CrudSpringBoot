package com.example.demo.user.Controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.Dto.UserDto;
import com.example.demo.user.Dto.UserViewDto;
import com.example.demo.user.Service.UserService;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody UserDto user) throws ConstraintViolationException {
		userService.saveUser(user.build());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping
	public ResponseEntity<Object> updateUser(@RequestBody UserDto user, @RequestParam Long userId) {
		UserViewDto userUpdateDto = userService.updateUser(userId, user);
		return ResponseEntity.status(HttpStatus.OK).body(userUpdateDto);
	}

	@DeleteMapping
	public ResponseEntity<Object> deleteUser(@RequestParam Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserId(@PathVariable Long id) {
		if (id <= 0) {
			throw new IllegalArgumentException("El ID debe ser positivo.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserId(id));
	}

}
