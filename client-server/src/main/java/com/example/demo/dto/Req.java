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
	
	//����ؼ� ����Ǵ� ���� �ޱ� ����
	private T requestsBody;
	
	@Data
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Header{
		private String responseCode;
	}
	
}
