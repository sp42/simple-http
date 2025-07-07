package com.ajaxjs.http.util;

import cn.hutool.core.exceptions.UtilException;
import com.xkcoding.http.SimpleHttpException;
import com.xkcoding.http.model.Constants;
import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@UtilityClass
public class HttpUtils {
	/**
	 * 编码
	 *
	 * @param value str
	 * @return encode str
	 */
	public String urlEncode(String value) {
		if (value == null)
			return "";

		try {
			String encoded = URLEncoder.encode(value, Constants.DEFAULT_ENCODING.displayName());
			return encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");
		} catch (UnsupportedEncodingException e) {
			throw new SimpleHttpException("Failed To Encode Uri", e);
		}
	}

	/**
	 * 解码URL<br>
	 * 将%开头的16进制表示的内容解码。
	 *
	 * @param url URL
	 * @return 解码后的URL
	 * @throws UtilException UnsupportedEncodingException
	 */
	public String urlDecode(String url) throws UtilException {
		if (isEmpty(url))
			return url;

		try {
			return URLDecoder.decode(url, Constants.DEFAULT_ENCODING.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new SimpleHttpException("Unsupported encoding", e);
		}
	}

	public boolean isEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

	public boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 如果给定字符串{@code str}中不包含{@code appendStr}，则在{@code str}后追加{@code appendStr}；
	 * 如果已包含{@code appendStr}，则在{@code str}后追加{@code otherwise}
	 *
	 * @param str       给定的字符串
	 * @param appendStr 需要追加的内容
	 * @param otherwise 当{@code appendStr}不满足时追加到{@code str}后的内容
	 * @return 追加后的字符串
	 */
	public String appendIfNotContain(String str, String appendStr, String otherwise) {
		if (isEmpty(str) || isEmpty(appendStr))
			return str;

		if (str.contains(appendStr))
			return str.concat(otherwise);

		return str.concat(appendStr);
	}
}
