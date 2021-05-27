package ru.example.demo.service.productModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
	public ProductModel addProduct(ProductModel productModel,MultipartFile multipartFile) {
					
		productModel.setLogo(StringUtils.cleanPath(multipartFile.getOriginalFilename()));	
		productModel.setSectionModel(secRepo.findBySection(productModel.getSection()));
		uploadPic(productModel.getSection(),productModel.getName_product(),multipartFile );
		
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
	public void updateProduct(ProductModel productModel, MultipartFile multipartFile) {		
		
		if(!multipartFile.isEmpty()) {	
			deleteOldPic(productModel.getSection(),productModel.getName_product(),productModel.getLogo(),multipartFile );
			productModel.setLogo(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
		}
		productModel.setSectionModel(secRepo.findBySection(productModel.getSection()));		
		uploadPic(productModel.getSection(),productModel.getName_product(),multipartFile);	
		prodRepo.save(productModel);				
	}	
	
	@Override
	public boolean isExist(long id) {
		return prodRepo.existsById(id);
	}
	
	private void uploadPic (String section, String nameProduct,MultipartFile multipartFile )  {
		//указывает расположение сохранения изображений и в случае чего создаёт нужные папки	
		try {
		if(multipartFile.isEmpty()) {
			return;
		}
		String uploadDir = "./products-logos/"+section+"/"+nameProduct;										
		Path uploadPath = Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		InputStream inputStream = multipartFile.getInputStream();
		Path filePath= uploadPath.resolve(StringUtils.cleanPath(multipartFile.getOriginalFilename()));						
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void deleteOldPic(String section, String nameProduct, String oldLogo,MultipartFile multipartFile)  {
		try {
		Path path = Paths.get("./products-logos/"+section+"/"+nameProduct+"/"+oldLogo);	
		Files.deleteIfExists(path);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
	
