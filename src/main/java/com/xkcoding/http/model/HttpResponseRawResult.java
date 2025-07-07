package com.xkcoding.http.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * The raw result of HTTP request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseRawResult {
	/**
	 * If this request is made successfully, this field will be true.
	 */
	private boolean success;

	/**
	 * The HTTP status code of the response.
	 */
	private int code;

	/**
	 * The HTTP headers of the response.
	 */
	private Map<String, List<String>> headers;

	/**
	 * The body of the response.
	 */
	private String body;

	/**
	 * The error message of the response. If it's successful, this field will be null.
	 */
	private String error;
}
