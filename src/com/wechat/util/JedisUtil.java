package com.wechat.util;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private static Logger logger = Logger.getLogger(JedisUtil.class);

	public static final String JS_TICKET_KEY = SystemProperty.getInstance("config").getProperty("jsticket.key");
	public static final String TOKEN_KEY = SystemProperty.getInstance("config").getProperty("token.key");
	private static JedisPool pool = null;
	
	static {
		String redisUrl = SystemProperty.getInstance("config").getProperty("redis.url");
		String password = SystemProperty.getInstance("config").getProperty("redis.password");
		int port = Integer.parseInt(SystemProperty.getInstance("config").getProperty("redis.port"));
		int timeout = Integer.parseInt(SystemProperty.getInstance("config").getProperty("redis.timeout"));
		
		// 连接池配置
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 最大连接数
		poolConfig.setMaxActive(Integer.parseInt(SystemProperty.getInstance("config").getProperty("redis.MaxActive")));
		// 最大空闲数
		poolConfig.setMaxIdle(Integer.parseInt(SystemProperty.getInstance("config").getProperty("redis.MaxIdle")));
		// 最小空闲数
		poolConfig.setMinIdle(Integer.parseInt(SystemProperty.getInstance("config").getProperty("redis.MinIdle")));
		// 最大等待时间
		poolConfig.setMaxWait(Long.parseLong(SystemProperty.getInstance("config").getProperty("redis.MaxWait")));
		// 返回连接之前测试是否连接成功
		poolConfig.setTestOnReturn(Boolean.parseBoolean(SystemProperty.getInstance("config").getProperty("redis.TestOnReturn")));
		// 打开连接之前测试是否连接成功
		poolConfig.setTestOnBorrow(Boolean.parseBoolean(SystemProperty.getInstance("config").getProperty("redis.TestOnBorrow")));
		// 创建连接池
	    pool = new JedisPool(poolConfig, redisUrl, port, timeout, password);
	}
	
	private JedisUtil(){}

	public static Jedis getInstance() {
		try {
			return pool.getResource();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return null;
	}

	public static void returnResource(Jedis jedis) {
		try {
			pool.returnResource(jedis);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static synchronized void setJsTicket(String value) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			jedis.set(JS_TICKET_KEY, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static String getJsTicket() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.get(JS_TICKET_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	public static synchronized void setToken(String value) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			jedis.set(TOKEN_KEY, value);
			logger.info(value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	public static String getToken() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.get(TOKEN_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	public static synchronized Long setTokenExpire(int seconds) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.expire(TOKEN_KEY, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	public static synchronized Long setJsTicketExpire(int seconds) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.expire(JS_TICKET_KEY, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	/**
	 *  查看某个TOKEN_KEY的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
	 *  @return
	 *  @author langkai
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年1月26日 下午8:33:18
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static Long getTokenTTL() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.ttl(TOKEN_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}

	/**
	 *  查看某个JS_TICKET_KEY的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
	 *  @return
	 *  @author langkai
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年1月26日 下午8:33:18
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static Long getJsTicketTTL() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.ttl(JS_TICKET_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
}
