package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("v1/users")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody DtoUser user) throws ConstraintViolationException {
		userService.saveUser(user.build());
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PutMapping
	public ResponseEntity<Object> updateUser(@RequestBody DtoUser user, @RequestParam Long userId) {
		userService.updateUser(userId, user);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@DeleteMapping
	public ResponseEntity<Object> deleteUser(@RequestParam Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
	}

	@GetMapping
	public ResponseEntity<Object> getUserId(@RequestParam Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserId(userId));
	}

}
