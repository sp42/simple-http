/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xkcoding.http;

import com.xkcoding.http.model.Constants;
import com.xkcoding.http.model.HttpConfig;
import com.xkcoding.http.model.HttpHeader;
import com.xkcoding.http.model.HttpResponseRawResult;
import com.xkcoding.http.support.HttpClientImpl;
import com.xkcoding.http.support.HutoolImpl;
import com.xkcoding.http.support.OkHttp3Impl;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * 请求工具类
 */
@UtilityClass
public class HttpRawHandler {
	private static AbstractHttp proxy;

	private void selectHttpProxy() {
		AbstractHttp defaultProxy = null;
		ClassLoader classLoader = HttpRawHandler.class.getClassLoader();

		// 基于 java 11 HttpClient
		if (isClassPresent("java.net.http.HttpClient", classLoader))
			defaultProxy = getHttpProxy(com.xkcoding.http.support.java11.HttpClientImpl.class);

		// 基于 okhttp3
		if (null == defaultProxy && isClassPresent("okhttp3.OkHttpClient", classLoader))
			defaultProxy = getHttpProxy(OkHttp3Impl.class);

		// 基于 httpclient
		if (null == defaultProxy && isClassPresent("org.apache.http.impl.client.HttpClients", classLoader))
			defaultProxy = getHttpProxy(HttpClientImpl.class);

		// 基于 hutool
		if (null == defaultProxy && isClassPresent("cn.hutool.http.HttpRequest", classLoader))
			defaultProxy = getHttpProxy(HutoolImpl.class);

		if (defaultProxy == null)
			throw new SimpleHttpException("Has no HttpImpl defined in environment!");

		proxy = defaultProxy;
	}

	/**
	 * 确定class是否可以被加载
	 *
	 * @param className   完整类名
	 * @param classLoader 类加载
	 * @return {boolean}
	 */
	public static boolean isClassPresent(String className, ClassLoader classLoader) {
		try {
			Class.forName(className, true, classLoader);
			return true;
		} catch (Throwable ex) {
			return false;
		}
	}

	private static <T extends AbstractHttp> AbstractHttp getHttpProxy(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Throwable e) {
			return null;
		}
	}

	public void setHttp(AbstractHttp http) {
		proxy = http;
	}

	private void checkHttpNotNull(Http proxy) {
		if (null == proxy)
			selectHttpProxy();
	}

	public void setConfig(HttpConfig httpConfig) {
		checkHttpNotNull(proxy);

		if (null == httpConfig)
			httpConfig = HttpConfig.builder().timeout(Constants.DEFAULT_TIMEOUT).build();

		proxy.setHttpConfig(httpConfig);
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public HttpResponseRawResult get(String url) {
		checkHttpNotNull(proxy);
		return proxy.get(url);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public HttpResponseRawResult get(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, encode);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public HttpResponseRawResult get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, header, encode);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public HttpResponseRawResult post(String url) {
		checkHttpNotNull(proxy);
		return proxy.post(url);
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	public HttpResponseRawResult post(String url, String data) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	public HttpResponseRawResult post(String url, String data, HttpHeader header) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data, header);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public HttpResponseRawResult post(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, encode);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public HttpResponseRawResult post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, header, encode);
	}
}
