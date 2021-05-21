package ru.example.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.example.demo.model.Roles;

public interface RoleRepository  extends JpaRepository<Roles, Long>{

	
	Set<Roles> findByRole (String roles);
	
}
