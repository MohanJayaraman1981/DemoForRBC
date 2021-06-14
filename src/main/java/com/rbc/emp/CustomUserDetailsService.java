package com.rbc.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rbc.emp.model.User;
import com.rbc.emp.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("customeruserdetailsservice.............."+username);
		User user = userRepo.findByEmail(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		System.out.println(user.getPassword());
		return new CustomUserDetails(user);
	}

}
