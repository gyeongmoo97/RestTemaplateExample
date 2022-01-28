package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Req;
import com.example.demo.dto.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/server")
@Slf4j
public class ServerApiController {
	
	
	@GetMapping("/hello")
	public User hello(
			@RequestParam String name, 
			@RequestParam int age) {
		User user = new User();
		user.setName(name);
		user.setAge(age);
		log.info("¾È³ç¼­¹ö");
		log.info("hello server");
		return user;
	}
	
	
	@PostMapping("/user/{name}/name/{age}")
	public User post(
			@RequestBody User user, 
			@PathVariable(name = "name") String userName, 
			@PathVariable(name = "age") int userAge) {
		
		log.info("UserName: {}, UserAge: {}  ", userName, userAge);
		log.info("clinent req : {} ", user);
		return user;
	}
	@PostMapping("/user-with-header/{name}/name/{age}")
	public User postWithHeader(
			@RequestBody User user, 
			@PathVariable(name = "name") String userName, 
			@PathVariable(name = "age") int userAge,
			@RequestHeader(name = "x-authorization") String auth,
			@RequestHeader(name = "custom-header") String custom
			) {
		
		log.info("UserName: {}, UserAge: {}  ", userName, userAge);
		log.info("clinent req : {} ", user);
		log.info("auth : {} ", auth);
		log.info("custom  : {} ", custom);
		return user;
	}
	
	@PostMapping("/generic/{name}/name/{age}")
	public Req<User> postGeneric(
			@RequestBody Req<User> user, 
			@PathVariable(name = "name") String userName, 
			@PathVariable(name = "age") int userAge,
			@RequestHeader(name = "x-authorization") String auth,
			@RequestHeader(name = "custom-header") String custom
			) {
		
		log.info("UserName: {}, UserAge: {}  ", userName, userAge);
		log.info("clinent req : {} ", user);
		log.info("auth : {} ", auth);
		log.info("custom  : {} ", custom);
		
		Req<User> response = new Req<>();
		response.setHeader(
				new Req.Header()
		);
		response.setRequestsBody(user.getRequestsBody());
		
		return response;
	}
}
