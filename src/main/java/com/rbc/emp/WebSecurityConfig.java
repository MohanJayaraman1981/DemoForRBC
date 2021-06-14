package com.rbc.emp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		System.out.println("Bean websecurityconfig......");
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		  http.authorizeRequests() 
		  		.antMatchers("/").authenticated()
		  		.anyRequest().permitAll() 
		  		.and() 
		  		.formLogin() 
		  			.usernameParameter("email")
		  			.defaultSuccessUrl("/home") .permitAll()
		  		.and()
		  		.logout().logoutSuccessUrl("/").permitAll();
		 
		
		/*
		 * http.authorizeRequests() .antMatchers("/").permitAll()
		 * .antMatchers("/h2-console/**").permitAll();
		 * 
		 * http.csrf().disable(); http.headers().frameOptions().disable();
		 */
	}
		
	
}
