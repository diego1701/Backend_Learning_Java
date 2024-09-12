package com.learning.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.app.entity.Admin;
import com.learning.app.entity.User;
import com.learning.app.entity.Users;
import com.learning.app.repository.AdminRepository;
import com.learning.app.repository.UsersRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Users user = userRepository.findByUser(username);
		if (user == null) {
			Admin admin = adminRepository.findByUser(username);
			if (admin == null) {
				throw new UsernameNotFoundException("Usuario no Encontrado");
			}
			return new User(admin.getUser(), admin.getPassword(), "ROLE_ADMIN");
		}
		return new User(user.getUser(), user.getPassword(), "ROLE_USER");
	}
}
