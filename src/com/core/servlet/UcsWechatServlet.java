package com.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.message.WechatMsg;
import com.wechat.ucs.Message;
import com.wechat.ucs.UcsReceive;
import com.wechat.util.JsonUtil;

public class UcsWechatServlet extends HttpServlet {

    private static final long serialVersionUID = 8651205041630169267L;

  
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        WechatMsg wechatMsg = null;
        try {
            String receiver = (String) request.getParameter("receiver");
            String content = (String) request.getParameter("content");
            String msgType = (String) request.getParameter("msgType");
            String picUrl = (String) request.getParameter("picUrl");
            Message msgBean = new Message();
            msgBean.setReceiver(receiver);
            msgBean.setContent(content);
            msgBean.setMessageType(msgType);
            msgBean.setPicUrl(picUrl);
       
            wechatMsg = UcsReceive.sendMsg(msgBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        PrintWriter out = response.getWriter();
        out.print(JsonUtil.toJson(wechatMsg));
        out.close();
    }
}
