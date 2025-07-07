package com.xkcoding.http.support.java11;

import com.xkcoding.http.model.HttpConfig;
import com.xkcoding.http.SimpleHttpException;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * 默认代理选择器
 */
public class DefaultProxySelector extends ProxySelector {
	private final HttpConfig httpConfig;

	public DefaultProxySelector(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
	}

	@Override
	public List<Proxy> select(URI uri) {
		return Collections.singletonList(httpConfig.getProxy());
	}

	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		throw new SimpleHttpException("Proxy connect failed!");
	}
}
