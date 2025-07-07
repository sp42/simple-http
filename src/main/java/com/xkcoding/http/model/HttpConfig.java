package com.xkcoding.http.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Proxy;

/**
 * Http 配置类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfig {
	/**
	 * 超时时长，单位毫秒
	 */
	private int timeout = Constants.DEFAULT_TIMEOUT;

	/**
	 * 代理配置
	 */
	private Proxy proxy;
}
