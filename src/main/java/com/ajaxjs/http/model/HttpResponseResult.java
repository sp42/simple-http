package com.ajaxjs.http.model;

import com.xkcoding.http.model.HttpResponseRawResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HttpResponseResult<T> extends HttpResponseRawResult {
	/**
	 * The result in Java Bean format. It's used for normal handling.
	 */
	T data;

	/**
	 * The result in Map format. It's used for error handling.
	 */
	Map<String, Object> mapData;
}
