package com.lk.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.lk.model.SysUser;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			SysUser user = (SysUser) authentication.getPrincipal();
			System.out.println("登陆成功，用户名：" + user.getUsername());
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(user.getUsername());
			/****下面方法是继承SavedRequestAwareAuthenticationSuccessHandler类实现的,用户记住上次跳转位置**/
//			super.onAuthenticationSuccess(request, response, authentication);
//			SavedRequest savedRequest = requestCache.getRequest(request, response);
//			if(savedRequest!=null){
//				String targetUrl = savedRequest.getRedirectUrl();
//				response.sendRedirect(targetUrl);
//			}else{
//				response.sendRedirect("/logout.html");
//			}
			
	}

}
