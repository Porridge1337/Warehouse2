package ru.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.example.demo.model.UserModel;
import ru.example.demo.userDetailsImplementation.UserServiceImpl;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserServiceImpl sectionService;
	
	@Override
	public boolean supports(Class<?> clazz) {		
		return UserModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		UserModel secModTarget = (UserModel) target;	
		System.out.println(secModTarget);
		if(sectionService.isExistByUsername(secModTarget.getUsername()) ) {
			errors.rejectValue("username","User exists", "This user is already exists");
		}		
	}
}
