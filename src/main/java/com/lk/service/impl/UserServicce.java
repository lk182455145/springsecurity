package com.lk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lk.model.SysUser;
import com.lk.repository.SysUserRepository;
import com.lk.service.IUserService;

@Service
public class UserServicce implements UserDetailsService , IUserService {
	
	@Autowired
	private SysUserRepository sysUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = sysUserRepository.findOneByUsername(username);
		if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
		return sysUser;
	}

	@Override
	public SysUser findOneById(Long id) {
		return sysUserRepository.getOne(id);
	}

}
