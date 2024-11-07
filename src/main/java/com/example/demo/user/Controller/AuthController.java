package com.example.demo.user.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.Dto.LoginReq;
import com.example.demo.user.Dto.LoginRes;
import com.example.demo.user.Service.UserService;

@RestController
@RequestMapping("v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Object> login(@RequestBody LoginReq loginReq) throws Exception {
		LoginRes res = userService.authEmailUser(loginReq);
		if (res == null) {
			return ResponseEntity.status(401).build();
		}
		return ResponseEntity.status(200).body(res);
	}

}
