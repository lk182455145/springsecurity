package com.lk.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lk.model.SysUser;
import com.lk.service.IUserService;

@RestController
@RequestMapping("/w/role")
public class TestController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping(value = "a")
	public String test(){
		return "aaaaaa";
	}

	@GetMapping(value = "{id}")
	public String find(@PathVariable(value = "id")Long id){
		SysUser user = userService.findOneById(id);
		return user.getUsername() + "-----" + user.getPassword();
	}
	
	@GetMapping(value = "user")
	public String user(@AuthenticationPrincipal SysUser user){
		return user.getUsername();
		
	}
}
