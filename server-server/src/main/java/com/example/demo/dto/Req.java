package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Req<T> {
	
	private Header header;
	
	//계속해서 변경되는 값을 받기 위함
	private T requestsBody;
	
	@Data
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Header{
		private String responseCode;
	}
	
}
