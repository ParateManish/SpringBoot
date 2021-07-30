package com.manish.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.manish.model.UserLogin;
import com.manish.repository.IUserLoginRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserLoginRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("CustomUserDetailsService.loadUserByUsername()-START");
		UserLogin user = userRepo.findByUserName(username);	
		if (user == null) {
			System.err.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}else {
			System.out.println("user is found :: "+user.toString());
		}
			
		System.out.println("CustomUserDetailsService.loadUserByUsername()-END");
		return new CustomUserDetails(user);
	}

}
