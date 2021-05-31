package ru.example.demo.service.sectionModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ru.example.demo.model.SectionModel;
import ru.example.demo.model.UserModel;
import ru.example.demo.repository.SectionRepository;
import ru.example.demo.repository.UserRepository;

@Service
public class SectionServiceImpl {

	@Autowired
	private SectionRepository sectionRepo;
	@Autowired
	private UserRepository userRepo;
	
	
	public SectionServiceImpl() {
		
	}

	
	public Page<SectionModel> findAllSectionPages(int pageNumber,String sortField, String sortDirection) {
		//Sort sort = Sort.by("section").ascending();
		Sort sort;
		if(sortDirection.equals("unsorted")) {
			sort = Sort.unsorted();
		}else {
			sort = sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		}
		
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 5,sort);
		
		return sectionRepo.findAll(pageable);
	}

	
	public SectionModel findBySectionId(long id) {
		
		return sectionRepo.findById(id).get();
	}
	
	public SectionModel findBySection(String secttionName) {
			
		return sectionRepo.findBySection(secttionName);
	}
		
	public UserModel findByUsername(String name) {
		
		return userRepo.findByUsername(name);
	}
		
	public void addSection(SectionModel sectionModel) {		
		sectionRepo.save(sectionModel);
	}

	public void deleteSection(long id) {
		sectionRepo.deleteById(id);
		
	}
	
	public void updateProduct(SectionModel sectionModel) {
		sectionRepo.save(sectionModel);	
	}
	
	public boolean isExist(long id) {
		
		return sectionRepo.existsById(id);
	}
		
	
}
