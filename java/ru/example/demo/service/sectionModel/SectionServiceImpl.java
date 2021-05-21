package ru.example.demo.service.sectionModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.example.demo.model.SectionModel;
import ru.example.demo.model.UserModel;
import ru.example.demo.repository.SectionRepository;
import ru.example.demo.repository.UserRepository;

@Service
public class SectionServiceImpl implements SectionService{

	@Autowired
	private SectionRepository sectionRepo;
	@Autowired
	private UserRepository userRepo;
	
	
	public SectionServiceImpl() {
		
	}

	@Override
	public List<SectionModel> findAllSection() {
		
		return sectionRepo.findAll();
	}

	@Override
	public SectionModel findBySectionId(long id) {
		
		return sectionRepo.findById(id).get();
	}

	@Override
		public SectionModel findBySection(String secttionName) {
			
		return sectionRepo.findBySection(secttionName);
	}
	
	@Override
	public UserModel findByUsername(String name) {
		
		return userRepo.findByUsername(name);
	}
	
	@Override
	public void addSection(SectionModel sectionModel) {		
		sectionRepo.save(sectionModel);
	}


	@Override
	public void deleteSection(long id) {
		sectionRepo.deleteById(id);
		
	}

	@Override
	public void updateProduct(SectionModel sectionModel) {
		sectionRepo.save(sectionModel);	
	}

	@Override
	public boolean isExist(long id) {
		
		return sectionRepo.existsById(id);
	}
		
	
}
