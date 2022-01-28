package com.example.demo.service;

import java.net.URI;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dto.Req;
import com.example.demo.dto.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestTemplateService {
	
	// http://localhost/api/server/hello
	//response
	
	
	public User hello() {
		//주소만들기
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:9090")
				.path("/api/server/hello")
				.queryParam("name", "경무")
				.queryParam("age", 26)
				.encode()
				.build()
				.toUri();
		
//		System.out.println(uri.toString());
		log.info("url : "+uri.toString());
		//RestTemplate 객체생성 - 이 객체는 다른 서버로 요청을 보낼때 사용함
		RestTemplate restTemplate = new RestTemplate();
		
		//특정 object로 지정하여 저장가능 하지만 ResponseEntity 사용하는게 활용하기가 더 좋다
//		String result = restTemplate.getForObject(uri, String.class);
		
		
		
		//ResponseEntity는 헤더나 다른 정보를 포함함 대신 <T>제네릭 안에 받을 객체으 타입명시하는 형태로 작성
		//result.getBody result.getHeader 이런식으로 작성하여 값 받아오기 가능
		ResponseEntity<User> result =restTemplate.getForEntity(uri, User.class);
		
		
		
		log.info("reslut : "+result);
		return result.getBody();
	}
	
	
	public User post() {
		//http://lacalhost:9090/api/server/user/{userId}/name/{userName}
		//주소만들기
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:9090")
				.path("/api/server/user/{name}/name/{age}")
				.encode() //인코딩
				.build() //빌드패턴으로 작성됨
				.expand("경무",26) // 작성된 값으로 Path Valiable 들어감
				.toUri();
		
		
		
		log.info(uri.toString());
		
		User req= new User();
		req.setName("경란");
		req.setAge(21);
		RestTemplate restTemplate = new RestTemplate();
		
		//uri 로 req를 보내서 (object 이지만 Object Mapper 에 의해 Json 으로 변경됨 ) User 클래스 형태로 응답 받겠다는 의미
		ResponseEntity<User> result =restTemplate.postForEntity(uri, req, User.class);
		log.info("result getStatusCode" + result.getStatusCode());
		log.info("result getHeaders"+ result.getHeaders());
		log.info("result getBody"+ result.getBody());
		
		return result.getBody();
	}
	
	// 보통 헤더에 무언가 추가해서 사용하는 경우가 많다 Auth Key 라던지 그것을 위한 exchange 배워본다
	public User exchange() {
		//http://lacalhost:9090/api/server/user/{userId}/name/{userName}
				//주소만들기
				URI uri = UriComponentsBuilder
						.fromUriString("http://localhost:9090")
						.path("/api/server/user-with-header/{name}/name/{age}")
						.encode() //인코딩
						.build() //빌드패턴으로 작성됨
						.expand("경무",26) // 작성된 값으로 Path Valiable 들어감
						.toUri();
				
				
				
				log.info(uri.toString());
				
				User req= new User();
				req.setName("경란");
				req.setAge(21);
				
				
				
				//요청 객체 생성 제너럴에는 Body에 들어갈 Dto 넣으면됨
				RequestEntity<User> requestEntity = RequestEntity
						.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.header("x-authorization", "hi")
						.header("custom-header", "bye")
						.body(req);
//						.header()
					
				
				RestTemplate restTemplate = new RestTemplate();
				
				
				//만들어둔 요청객체를 Post 로 보내고 응답에 대하여 User 클래스로 받는다.
				ResponseEntity<User> result = restTemplate.exchange(requestEntity, User.class);
				
				
				return result.getBody();
	}
	
	
	
	//Body의 내용이 계속해서 변경될 때
	//ex name와 age 혹은 book과 page 가 온다. 둘다 받아야할 때
	
	
	//전체적 구조
	/*
	Server 에 요청할 URI를 UriComponentsBuilder 로 생성함
	Dto 인스턴스 생성
	제네릭이 들어가는 Req객체에 Header 과 Body 부분 생성 후 Body에 Dto 인스턴스 삽입
	요청을 보낼 Req 객체를 RequestEntity(RequestEntity 객체는 URL정보를 포함하고 HTTP request가 된다) Body 에 넣는다. 
	https://devlog-wjdrbs96.tistory.com/182 참고
	
	()
	
	
	*/
	
	public Req<User> genericExchange() {
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:9090")
				.path("/api/server/generic/{name}/name/{age}")
				.encode() //인코딩
				.build() //빌드패턴으로 작성됨
				.expand("경무",26) // 작성된 값으로 Path Valiable 들어감
				.toUri();
		
		
		
		log.info(uri.toString());
		
		User UserReq= new User();
		UserReq.setName("경란");
		UserReq.setAge(21);
		
		Req<User> reqsts = new Req<User>();
		reqsts.setHeader(
				new Req.Header()
		);
		reqsts.setRequestsBody(
				UserReq
		);
		
		//요청 객체 생성 제너럴에는 Body에 들어갈 Dto 넣으면됨
		RequestEntity<Req<User>> requestEntity = RequestEntity
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.header("x-authorization", "hi")
				.header("custom-header", "bye")
				.body(reqsts);
		
		
		RestTemplate restTemplate = new RestTemplate();
		
//		제네릭에는 .class 못붙힘 제네릭에 대응하기 위해
		//new ParameterizedTypeReference
//		ResponseEntity<Req<User>> response
//			= restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<User>>() {
//			});
		
		
		
		
		//response 즉 응답은 requestEntity 객체로 생성된 응답의 Body가 Req<User> 형태로 돌아오는것
		
		ResponseEntity<Req<User>> response
			= restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<User>>() {
			});
		
		//response 에서 body를 까보면 Req<User>가 있고 거기서 getter로 RequestsBody를 얻을 수 있다.
		return response.getBody();
		
	}
	
}
