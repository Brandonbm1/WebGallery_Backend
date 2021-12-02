package com.webgallery.demo.security.services;
//tomado de https://www.bezkoder.com/spring-boot-jwt-authentication/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webgallery.demo.security.models.User;
import com.webgallery.demo.security.respository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new SessionAuthenticationException("Los datos son incorrectos para: " + email));
		

		return UserDetailsImpl.build(user);
	}

}
