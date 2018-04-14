package com.lk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lk.model.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
	
	SysUser findOneByUsername(String username);
	
}
