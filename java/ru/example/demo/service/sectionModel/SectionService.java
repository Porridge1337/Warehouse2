package ru.example.demo.service.sectionModel;

import java.util.List;

import ru.example.demo.model.SectionModel;
import ru.example.demo.model.UserModel;

public interface SectionService {

	List<SectionModel> findAllSection();
	SectionModel findBySectionId(long id);
	SectionModel findBySection(String secttionName);
	
	UserModel findByUsername(String name);
		
	boolean isExist(long id);
	
	void addSection(SectionModel sectionModel);	
	void deleteSection(long id);
	void updateProduct(SectionModel sectionModel);
	
}
