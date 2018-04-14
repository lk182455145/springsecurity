package com.lk.authenticationprovider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

public class DaoImplAuthenticationProvider extends DaoAuthenticationProvider{

	public DaoImplAuthenticationProvider(){
		System.out.println("------加载DaoImplAuthenticationProvider-----------");
	}
}
