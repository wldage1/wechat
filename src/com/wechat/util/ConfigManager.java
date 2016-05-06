package com.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 操作配置文件工具类
 * 
 */
public class ConfigManager {

	private static Logger log = LoggerFactory.getLogger(ConfigManager.class);

	/**
	 * 获config取配置文件信息
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		Properties p = null;
		InputStream ip = ConfigManager.class
				.getResourceAsStream("/config.properties");
		try {
			p = new Properties();

			p.load(ip);
			ip.close();

		} catch (IOException e) {
			p = null;
			log.error("读取config.properties失败:{}", e);
		}
		return (p.containsKey(key)) ? p.getProperty(key) : "";
	}

}