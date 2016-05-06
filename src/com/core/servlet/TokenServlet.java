package com.core.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.core.service.TokenThread;


/**
 * 
 * 初始化Token持久化 的进程
 * 
 */
public class TokenServlet extends HttpServlet {

	private static final long serialVersionUID = 2446987914388183601L;

	public void init() throws ServletException {
		System.out.println("启动线程。。。");
		// 启动定时获取access_token的线程
		new Thread(new TokenThread()).start();

	}
}