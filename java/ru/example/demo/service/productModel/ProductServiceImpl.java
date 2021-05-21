package ru.example.demo.service.productModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.example.demo.model.ProductModel;
import ru.example.demo.model.SectionModel;
import ru.example.demo.repository.ProductRepository;
import ru.example.demo.repository.SectionRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private SectionRepository secRepo;	
	@Autowired
	private ProductRepository prodRepo;
	
	public ProductServiceImpl() {
		
	}
	
	@Override
	public ProductModel addProduct(ProductModel productModel) {
					
		productModel.setSectionModel(secRepo.findBySection(productModel.getSection()));
		
		return prodRepo.save(productModel);
	}

	@Override
	public void deleteProduct(long id) {
		prodRepo.deleteById(id);
	}

	@Override
	public List<ProductModel> findAllProducts() {		
		return prodRepo.findAll();
	}

	@Override
	public List<SectionModel> findAllSections() {
		
		return secRepo.findAll();
	}

	@Override
	public List<ProductModel> findProductBySectionId(long id) {
		
		return prodRepo.findProductsModelBySectionId(id);
	}

	@Override
	public ProductModel findProductById(long id) {		
		
		return prodRepo.findById(id).get();			
	}

	@Override
	public void updateProduct(ProductModel productModel) {
	
		productModel.setSectionModel(secRepo.findBySection(productModel.getSection()));
		prodRepo.save(productModel);
		
	}
	
	@Override
	public boolean isExist(long id) {
		return prodRepo.existsById(id);
	}
	
}
