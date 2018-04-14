package com.lk.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
//		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(403);
		System.out.println(exception.getMessage());
//		System.out.println(request.getSession(false).getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
//		response.getWriter().write(exception.getMessage());
		response.sendRedirect("/login?error");
	}

}
