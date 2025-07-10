package com.ajaxjs.http;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHttpSender {

	@Data
	class Person {
		String name;
		String city;
		int age;
	}

	@Test
	void testQueryParams() {
		HttpSender httpSender = HttpSender.get("https://www.baidu.com").queryParams("name", "Alice", "age", 25, "city", "New York");
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getQueryParams());

		httpSender = HttpSender.get("https://www.baidu.com").queryParams("name=Alice&age=25&city=New York");
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getQueryParams());

		Map<String, Object> map = new HashMap<>();
		map.put("name", "Alice");
		map.put("age", 25);
		map.put("city", "New York");

		httpSender = HttpSender.get("https://www.baidu.com").queryParams(map);
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getQueryParams());

		Map<String, String> map2 = new HashMap<>();
		map.put("name", "Alice");
		map.put("age", "25");
		map.put("city", "New York");

		httpSender = HttpSender.get("https://www.baidu.com").queryParams(map);
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getQueryParams());

		Person person = new Person();
		person.setName("Alice");
		person.setAge(25);
		person.setCity("New York");

		httpSender = HttpSender.get("https://www.baidu.com").queryParams(person);
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getQueryParams());
	}

	@Test
	void testFormBody() {
		HttpSender httpSender = HttpSender.get("https://www.baidu.com").formBody("name", "Alice", "age", 25, "city", "New York");
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getBodyParams());

		httpSender = HttpSender.get("https://www.baidu.com").formBody("name=Alice&age=25&city=New York");
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getBodyParams());

		Map<String, Object> map = new HashMap<>();
		map.put("name", "Alice");
		map.put("age", 25);
		map.put("city", "New York");

		httpSender = HttpSender.get("https://www.baidu.com").formBody(map);
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getBodyParams());

		Map<String, String> map2 = new HashMap<>();
		map.put("name", "Alice");
		map.put("age", "25");
		map.put("city", "New York");

		httpSender = HttpSender.get("https://www.baidu.com").formBody(map);
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getBodyParams());

		Person person = new Person();
		person.setName("Alice");
		person.setAge(25);
		person.setCity("New York");

		httpSender = HttpSender.get("https://www.baidu.com").formBody(person);
		assertEquals("city=New%20York&name=Alice&age=25", httpSender.getBodyParams());
	}

	@Test
	void testJsonBody() {
		HttpSender httpSender = HttpSender.get("https://www.baidu.com").jsonBody("name", "Alice", "age", 25, "city", "New York");
		assertEquals("{\"city\":\"New York\",\"name\":\"Alice\",\"age\":25}", httpSender.getBodyParams());

		httpSender = HttpSender.get("https://www.baidu.com").jsonBody("name=Alice&age=25&city=New York");
		assertEquals("{\"city\":\"New York\",\"name\":\"Alice\",\"age\":\"25\"}", httpSender.getBodyParams());

		Map<String, Object> map = new HashMap<>();
		map.put("name", "Alice");
		map.put("age", 25);
		map.put("city", "New York");

		httpSender = HttpSender.get("https://www.baidu.com").jsonBody(map);
		assertEquals("{\"city\":\"New York\",\"name\":\"Alice\",\"age\":25}", httpSender.getBodyParams());

		Map<String, String> map2 = new HashMap<>();
		map.put("name", "Alice");
		map.put("age", "25");
		map.put("city", "New York");

		httpSender = HttpSender.get("https://www.baidu.com").jsonBody(map);
		assertEquals("{\"city\":\"New York\",\"name\":\"Alice\",\"age\":\"25\"}", httpSender.getBodyParams());

		Person person = new Person();
		person.setName("Alice");
		person.setAge(25);
		person.setCity("New York");

		httpSender = HttpSender.get("https://www.baidu.com").jsonBody(person);
		assertEquals("{\"name\":\"Alice\",\"city\":\"New York\",\"age\":25}", httpSender.getBodyParams());
	}
	@Test
	void testGet() {
		HttpSender.get("https://www.baidu.com");
	}
}
