package ru.example.demo.userDetailsImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.example.demo.model.UserModel;
import ru.example.demo.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{//загрузка и проверка логина

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModel user = userRepo.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("could not find user");
		}
		
		return new MyUserDetails(user);
	}


}
