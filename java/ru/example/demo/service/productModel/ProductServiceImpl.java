package ru.example.demo.service.productModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ru.example.demo.model.ProductModel;
import ru.example.demo.model.SectionModel;
import ru.example.demo.repository.ProductRepository;
import ru.example.demo.repository.SectionRepository;

@Service
public class ProductServiceImpl {

	@Autowired
	private SectionRepository secRepo;	
	@Autowired
	private ProductRepository prodRepo;
	
	public ProductServiceImpl() {
		
	}
	
	
	public ProductModel addProduct(ProductModel productModel,MultipartFile multipartFile) {
					
		productModel.setLogo(StringUtils.cleanPath(multipartFile.getOriginalFilename()));	
		productModel.setSectionModel(secRepo.findBySection(productModel.getSection()));
		uploadPic(productModel.getSection(),productModel.getNameproduct(),multipartFile );
		
		return prodRepo.save(productModel);
	}

	
	public void deleteProduct(long id) {
		prodRepo.deleteById(id);
	}

	
	public Page<ProductModel> findAllProducts(int pageNumber, String sortField, String sortDir) {	
		Sort sort;
		
		if(sortDir.equals("unsorted")) {
			sort = Sort.unsorted();
		}else {
			sort = sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		}				
		Pageable pageable = PageRequest.of(pageNumber - 1, 5,sort);
		
		return prodRepo.findAll(pageable);
	}

	
	public List<SectionModel> findAllSections() {
		
		return secRepo.findAll();
	}

	
	public Page<ProductModel> findProductBySectionId(long sec_id, int pageNumber,String sortField,String sortDir) {
		Sort sort;
		if(sortDir.equals("unsorted")) {
			sort = Sort.unsorted();
		}else {
			sort = sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		}	
		
		Pageable pageable = PageRequest.of(pageNumber-1, 5, sort);
				
		return prodRepo.findProductsModelBySectionId(sec_id,pageable);
	}
	public int getSizeOfProductsModelBySectionId(long sec_id){
		return prodRepo.findProductsModelBySectionId(sec_id).size();
	}
	
	
	public ProductModel findProductById(long id) {		
		
		return prodRepo.findById(id).get();			
	}

	
	public void updateProduct(ProductModel productModel, MultipartFile multipartFile) {		
		
		if(!multipartFile.isEmpty()) {	
			deleteOldPic(productModel.getSection(),productModel.getNameproduct(),productModel.getLogo(),multipartFile );
			productModel.setLogo(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
		}
		productModel.setSectionModel(secRepo.findBySection(productModel.getSection()));		
		uploadPic(productModel.getSection(),productModel.getNameproduct(),multipartFile);	
		prodRepo.save(productModel);				
	}	
	
	
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
	
