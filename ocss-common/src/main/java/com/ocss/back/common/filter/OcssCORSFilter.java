package com.ocss.back.common.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tying on 2017/12/30.
 * 跨域filter
 */
@Component
public class OcssCORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = (String) servletRequest.getRemoteHost() + ":" + servletRequest.getRemotePort();
        //表明允许所有请求跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        //允许POST, GET, OPTIONS, PUT, DELETE发去外域请求
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        // 允许表明在3600秒内，不需要再发送预检验请求，可以缓存该结果（上面的资料上我们知道CROS协议中，一个AJAX请求被分成了第一步的OPTION预检测请求和正式请求）
        response.setHeader("Access-Control-Max-Age", "3600");
        // 允许跨域请求包含的头
        response.setHeader("Access-Control-Allow-Headers", "Accept, Origin, X-Requested-With, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
