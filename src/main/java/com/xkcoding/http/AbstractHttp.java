package com.xkcoding.http;

import com.xkcoding.http.model.HttpConfig;

/**
 * HTTP 抽象类
 */
public abstract class AbstractHttp implements Http {
	protected HttpConfig httpConfig;

	public AbstractHttp(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
	}

	public void setHttpConfig(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
	}
}
