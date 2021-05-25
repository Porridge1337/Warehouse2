package ru.example.demo.service.productModel;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ru.example.demo.model.ProductModel;
import ru.example.demo.model.SectionModel;

public interface ProductService {

	ProductModel addProduct(ProductModel productModel, String fileName);
	
	List<SectionModel> findAllSections();
	List<ProductModel> findAllProducts();	
	List<ProductModel> findProductBySectionId(long id);
	ProductModel findProductById(long id);
	
	void deleteProduct(long id);
	void updateProduct(ProductModel productModel,  MultipartFile multipartFile);
	
	boolean isExist(long id);
}
