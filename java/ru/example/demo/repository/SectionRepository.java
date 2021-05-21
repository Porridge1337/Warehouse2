package ru.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.example.demo.model.SectionModel;

public interface SectionRepository extends JpaRepository<SectionModel, Long>{

	SectionModel findBySection(String secttionName);
	
}
