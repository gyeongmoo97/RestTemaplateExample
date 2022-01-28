package com.example.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Req;
import com.example.demo.dto.User;
import com.example.demo.service.RestTemplateService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/client")
@Slf4j
public class ApiController {
	
	@Autowired
	private RestTemplateService restTemplateService;
	
	@GetMapping("")
	public User getHello() {
		log.info("클라이언트 메서드 실행");
		return restTemplateService.hello();
	}

	@PostMapping("")
	public User postHello() {
		log.info("클라이언트 메서드 실행");
		return restTemplateService.post();
	}
	
	@PostMapping("/with-header")
	public User postWithHeader() {
		log.info("클라이언트 메서드 실행");
		return restTemplateService.exchange();
	}
	
	@PostMapping("/generic")
	public Req<User> postGenericr() {
		log.info("postGenericr 클라이언트 메서드 실행");
		return restTemplateService.genericExchange();
	}
}
