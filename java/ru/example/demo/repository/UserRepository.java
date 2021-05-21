package ru.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.example.demo.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{

	
	UserModel findByUsername(String username);
	
}
