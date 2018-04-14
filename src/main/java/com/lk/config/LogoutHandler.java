package com.lk.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.lk.model.SysUser;

public class LogoutHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		Object obj = authentication.getPrincipal();
		if(obj instanceof SysUser){
			System.out.println(((SysUser) obj).getUsername());
		}
		response.sendRedirect("/login?logout");
	}

}
