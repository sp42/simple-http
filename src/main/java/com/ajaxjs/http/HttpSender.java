package com.ajaxjs.http;

import com.ajaxjs.http.model.HttpMethod;
import com.ajaxjs.http.util.MapUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xkcoding.http.model.Constants;
import com.xkcoding.http.model.HttpResponseRawResult;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.ajaxjs.http.util.MapUtil.OBJECT_MAPPER;

@Slf4j
public class HttpSender {
	/**
	 * The url that requests to
	 */
	String url;

	HttpMethod method;

	public HttpSender(String url, HttpMethod method) {
		this.url = url;
		this.method = method;
	}

	/**
	 * Create a GET request with the url.
	 *
	 * @param url The url that requests to
	 * @return HttpSender
	 */
	public static HttpSender get(String url) {
		return new HttpSender(url, HttpMethod.GET);
	}

	/**
	 * Create a POST request with the url.
	 *
	 * @param url The url that requests to
	 * @return HttpSender
	 */
	public static HttpSender post(String url) {
		return new HttpSender(url, HttpMethod.POST);
	}

	/**
	 * Create a PUT request with the url.
	 *
	 * @param url The url that requests to
	 * @return HttpSender
	 */
	public static HttpSender put(String url) {
		return new HttpSender(url, HttpMethod.PUT);
	}

	/**
	 * Create a DELETE request with the url.
	 *
	 * @param url The url that requests to
	 * @return HttpSender
	 */
	public static HttpSender delete(String url) {
		return new HttpSender(url, HttpMethod.DELETE);
	}

	/**
	 * The parameters of Query String, for any kind of HTTP request
	 */
	String queryParams;

	/**
	 * The parameters of raw body, for POST, PUT, PATCH
	 */
	String bodyParams;

	/**
	 * The parameters of header, for any kind of HTTP request
	 */
	Map<String, String> headerParams;

	public String getQueryParams() {
		return queryParams;
	}

	public String getBodyParams() {
		return bodyParams;
	}

	public Map<String, String> getHeader() {
		return headerParams;
	}

	/**
	 * Weather encodes query params
	 */
	boolean encodeQueryString = true;

	public HttpSender encodeQueryString(boolean encodeQueryString) {
		this.encodeQueryString = encodeQueryString;
		return this;
	}

	public HttpSender queryParams(Object... params) {
		if (params.length % 2 != 0)
			throw new IllegalArgumentException("The total number of params must be a even number.");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.length; i += 2) {
			Object key = params[i];
			Object value = params[i + 1];

			if (sb.length() > 0)
				sb.append("&");

			sb.append(key != null ? key.toString() : "");
			sb.append("=");
			sb.append(value != null ? value.toString() : "");
		}

		return queryParams(sb.toString());
	}

	@SuppressWarnings("uncehcked")
	public HttpSender queryParams(Object params) {
		if (params instanceof String) {
			Map<String, String> m = MapUtil.parseStringToMap((String) params, false);

			return queryParams(m);
		} else if (params instanceof Map) {
			Map<String, Object> m = (Map<String, Object>) params;
			Map<String, String> s = new HashMap<>();
			m.forEach((k, v) -> s.put(k, v.toString()));

			queryParams = MapUtil.parseMapToString(s, encodeQueryString);

			if (url.contains("?"))
				url += (url.endsWith("&") ? queryParams : "&" + queryParams);
			else
				url += "?" + queryParams;

			return this;
		} else  // bean
			return queryParams(MapUtil.bean2map(params));
	}

	String contentType;

	public HttpSender jsonBody(Object params) {
		contentType = Constants.CONTENT_TYPE_JSON;

		if (params instanceof String) {
			String str = (String) params;

			if (str.startsWith("{") || str.startsWith("[")) { // JSON
				bodyParams = str;
				return this;
			} else { // a=1&b=c
				Map<String, String> m = MapUtil.parseStringToMap((String) params, false);

				return jsonBody(m);
			}
		} else { // map or bean
			try {
				bodyParams = OBJECT_MAPPER.writeValueAsString(params); // map to json
				return this;
			} catch (JsonProcessingException e) {
				log.warn("Map or Bean can't transform to JSON! The Object is " + params, e);
				throw new RuntimeException(e);
			}
		}
	}

	public HttpSender jsonBody(Object... params) {
		if (params.length % 2 != 0)
			throw new IllegalArgumentException("The total number of params must be a even number.");

		Map<String, Object> map = new HashMap<>();

		for (int i = 0; i < params.length; i += 2) {
			Object key = params[i];
			Object value = params[i + 1];

			if (!(key instanceof String))
				throw new IllegalArgumentException("键必须是 String 类型，但实际是：" + key.getClass());

			map.put((String) key, value);
		}

		return jsonBody(map);
	}

	public HttpSender formBody(Object... params) {
		if (params.length % 2 != 0)
			throw new IllegalArgumentException("The total number of params must be a even number.");

		Map<String, Object> map = new HashMap<>();

		for (int i = 0; i < params.length; i += 2) {
			Object key = params[i];
			Object value = params[i + 1];

			map.put(key.toString(), value);
		}

		return formBody(map);
	}

	@SuppressWarnings("uncehcked")
	public HttpSender formBody(Object params) {
		contentType = Constants.CONTENT_TYPE_JSON;

		if (params instanceof String) {
			Map<String, String> m = MapUtil.parseStringToMap((String) params, false);

			return formBody(m);
		} else if (params instanceof Map) {
			Map<String, Object> m = (Map<String, Object>) params;
			Map<String, String> s = new HashMap<>();
			m.forEach((k, v) -> s.put(k, v.toString()));

			bodyParams = MapUtil.parseMapToString(s, encodeQueryString);

			return this;
		} else  // bean
			return formBody(MapUtil.bean2map(params));
	}

	public HttpSender header(Object... params) {
		if (params.length % 2 != 0)
			throw new IllegalArgumentException("The total number of params must be a even number.");

		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < params.length; i += 2) {
			Object key = params[i];
			Object value = params[i + 1];

			map.put(key.toString(), value.toString());
		}

		return this;
	}

	public HttpSender header(Map<String, String> map) {
		headerParams = map;
		return this;
	}

	HttpResponseRawResult response;

	public void request() {

	}

	public String getStr() {
		return response.getBody();
	}

	public <T> T get(Class<T> clazz) {
		String json = response.getBody();

		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			log.warn("JSON String can't transform to a Java bean! The JSON is " + json, e);
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> getMap() {
		String json = response.getBody();

		try {
			return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonProcessingException e) {
			log.warn("JSON String can't transform to a Java bean! The JSON is " + json, e);
			throw new RuntimeException(e);
		}
	}
}
