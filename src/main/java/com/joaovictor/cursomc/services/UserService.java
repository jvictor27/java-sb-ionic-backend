package com.joaovictor.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.joaovictor.cursomc.secutiry.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
