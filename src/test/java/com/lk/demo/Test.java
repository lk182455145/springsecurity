package com.lk.demo;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {

	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("admin");
        System.out.println("加密后的密码:" + encode);
        System.out.println("bcrypt密码对比:" + passwordEncoder.matches("admin", encode));

        String md5Password = "{MD5}5f4dcc3b5aa765d61d8327deb882cf99";//MD5加密前的密码为:password
        System.out.println("MD5密码对比:" + passwordEncoder.matches("admin", encode));
        
        System.out.println(passwordEncoder.matches("password", md5Password));
		
	}
}
