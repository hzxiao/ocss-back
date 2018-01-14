package com.ocss.back.filter.interceptor;


import com.ocss.back.common.util.JSONUtils;
import com.ocss.back.common.util.ResponseResult;
import com.ocss.back.dao.mapper.UserDao;
import com.ocss.back.filter.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class HeaderTokenInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(HeaderTokenInterceptor.class);
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
       // String  contentPath=httpServletRequest.getContextPath();
        // System.out.println("contenxPath:"+contentPath);
        String requestURI=httpServletRequest.getRequestURI();
        String tokenStr=httpServletRequest.getParameter("token");
        String token="";
        if(requestURI.contains("/api/")){
            token=httpServletRequest.getHeader("token");
            if(token==null && tokenStr==null){
                System.out.println("real token:======================is null");
//                String str="{'errorCode':0101,'msg':'缺少token，无法验证','data':null}";
                dealErrorReturn(httpServletRequest,httpServletResponse,ResponseResult.createLoginRequireResult(null));
                return false;
            }
            if (tokenStr!=null){
                token = tokenStr;
            }
            String username = null;
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
            if (username==null){
//                    String str="{'errorCode':0102,'msg':'token无效','data':null}";
                dealErrorReturn(httpServletRequest,httpServletResponse, ResponseResult.createLoginRequireResult(null));
            }
            Map<String,Object> data = userDao.selectUserByUsername(username);
            if (data==null){
//                    String str="{'errorCode':0103,'msg':'用户不存在','data':null}";
                dealErrorReturn(httpServletRequest,httpServletResponse,ResponseResult.createLoginRequireResult(null));
            }

            token=jwtUtil.updateToken(token);
            System.out.println("real token:=============================="+token);
            System.out.println("real ohter:=============================="+httpServletRequest.getHeader("Cookie"));
        }

        httpServletResponse.setHeader("token",token);
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    // 检测到没有token，直接返回不验证
    public void dealErrorReturn(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,ResponseResult obj){

        String json = "";
        if (obj!=null){
            json = JSONUtils.toJSONObject(obj).toString();
        }
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html; charset=utf-8");
        try {
            writer = httpServletResponse.getWriter();
            writer.print(json);

        } catch (IOException ex) {
            logger.error("response error",ex);
        } finally {
            if (writer != null)
                writer.close();
        }
    }


}