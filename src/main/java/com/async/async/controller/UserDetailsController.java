package com.async.async.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.async.async.entity.UserDetails;
import com.async.async.service.UserDetailsService;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsController {

	@Autowired
	private UserDetailsService userService;
	
	@GetMapping("/users")
	public CompletableFuture<List<UserDetails>> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/users")
	public ResponseEntity createUsers(@RequestBody MultipartFile[] files) throws Exception {
		return userService.saveUserDetails(files);
	}
}
