package com.xkcoding.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 响应封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleHttpResponse {
	private boolean success;
	private int code;
	private Map<String, List<String>> headers;
	private String body;
	private String error;
}
