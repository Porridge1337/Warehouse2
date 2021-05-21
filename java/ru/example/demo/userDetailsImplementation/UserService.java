package ru.example.demo.userDetailsImplementation;


import java.util.List;

import ru.example.demo.model.Roles;
import ru.example.demo.model.UserModel;

public interface UserService { 

	UserModel saveWithDefaultRole (UserModel userModel); //служит для создания пользователя
	UserModel update(UserModel userModel); // обновение данных пользователя
	UserModel getUserModel(long id);
	void deleteUser(long id);
	List<UserModel> getAllUsers();
	List<Roles> getRoles();
	
}
