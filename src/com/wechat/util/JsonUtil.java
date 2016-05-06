package com.wechat.util;

import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json 工具类
 * 
 */
public class JsonUtil {

	private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * 将对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, obj);
		} catch (Exception e) {
			mapper = null;
			writer = null;
			log.error("toJson失败：{}", e);
		}

		return writer.toString();
	}

	/**
	 * 将json字符串转换成对象
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static <T> T toBean(String str, Class<T> c) {
		ObjectMapper mapper = new ObjectMapper();
		T t = null;
		try {
			t = mapper.readValue(str, c);
		} catch (Exception e) {
			log.info("toBean失败:{}", e);
		}
		return t;
	}

	/**
	 * 将json字符串转换成Map对象
	 * 
	 * @param data
	 * @return
	 */
	public static Map<?, ?> toMap(String data) {
		Map<?, ?> map = null;
		if (data == null) {
			return map;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(data, Map.class);
		} catch (Exception e) {
			log.error("toMap失败:{}", e);
		}
		return map;
	}
}
