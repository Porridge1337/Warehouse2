package ru.example.demo.userDetailsImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.example.demo.model.Roles;
import ru.example.demo.model.UserModel;
import ru.example.demo.repository.RoleRepository;
import ru.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserModel saveWithDefaultRole(UserModel userModel) {		
		userModel.setRole(roleRepo.findByRole("USER"));
		userModel.setActives(true);
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));		
		
		return userRepo.save(userModel);
	}
	@Override
	public UserModel update(UserModel userModel) { 
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		
		return userRepo.save(userModel);
	}
	
	@Override
	public UserModel getUserModel(long id) {
		UserModel userModel = userRepo.findById(id).get();
		userModel.setPassword("");
		
		return userModel;
	}
	
	@Override
	public List<Roles> getRoles() {
		
		return roleRepo.findAll();
	}
	@Override
	public List<UserModel> getAllUsers() {
		
		return userRepo.findAll();
	}
	@Override
	public void deleteUser(long id) {
		 userRepo.deleteById(id);
	}
}
