package ru.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ru.example.demo.model.ProductModel;
import ru.example.demo.model.SectionModel;
import ru.example.demo.service.productModel.ProductService;
import ru.example.demo.service.sectionModel.SectionService;

@Controller
@RequestMapping("/section")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private SectionService sectionService;
	
	@GetMapping("/products")
	public String getProductsPage(@ModelAttribute(name = "product") ProductModel productModel ,Model model) {
			
		model.addAttribute("product",productModel);		
		model.addAttribute("products",productService.findAllProducts());				
		model.addAttribute("sect",productService.findAllSections());
		
		return "products/product";
	}
	
	@PostMapping("/products/add")
	public String addProduct(@ModelAttribute("product")ProductModel productModel,
			@RequestParam("fileImage")MultipartFile multipartFile) throws IOException { 
					
		productService.addProduct(productModel,StringUtils.cleanPath(multipartFile.getOriginalFilename()));
		calculateQuantities(sectionService.findBySection(productModel.getSection()).getId());
		uploadPic(productModel.getSection(),productModel.getName_product(),StringUtils.cleanPath(multipartFile.getOriginalFilename()) ,multipartFile );
												
		return "redirect:/section/products";
	}
	
	@GetMapping("/products/{id}")
	public String getProductsById(@PathVariable("id")long id,@ModelAttribute(name = "product") ProductModel productModel , Model model) {
		
		model.addAttribute("products", productService.findProductBySectionId(id));
		model.addAttribute("product",productModel);
		model.addAttribute("sect",productService.findAllSections());
		
		return"products/product";
	}
	
	@DeleteMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable("id")long id) {
		
		long section_id = sectionService.findBySection(productService.findProductById(id).getSection()).getId();
		
		productService.deleteProduct(id);		
		calculateQuantities(section_id);
				
		return"redirect:/section/products";
	}
	
	@GetMapping("/products/update/{id}") 
	public String getProductUpdatePage(@PathVariable("id") long id,Model model) {
		
		if(productService.isExist(id)) {					
			model.addAttribute("update", productService.findProductById(id));
			model.addAttribute("sect",productService.findAllSections());
		return "products/productUpdate";
		}else return "redirect:/section/products";
						
	}
	
	@PatchMapping("/products/update/{id}")
	public String updateProduct(@ModelAttribute(name = "update")ProductModel productModel) {
		
		productService.updateProduct(productModel);
		
		return "redirect:/section/products/"+productModel.getId();
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////
	private void calculateQuantities(long section_id) { //высчитывает колличество продуктов в таблицу секции
		int ammountProductsModel = productService.findProductBySectionId(section_id).size();		
		SectionModel sectionModel = sectionService.findBySectionId(section_id);
		sectionModel.setQuantity(ammountProductsModel);
		sectionService.updateProduct(sectionModel);				
	}
	private void uploadPic (String section, String nameProduct,String fileName,MultipartFile multipartFile ) throws IOException {//указывает расположение сохранения картинок
																									//и в случае чего создаёт нужные папки																						
		if(multipartFile.isEmpty()) {
			return;
		}
		String uploadDir = "./products-logos/"+section+"/"+nameProduct;										
		Path uploadPath = Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		InputStream inputStream = multipartFile.getInputStream();
		Path filePath= uploadPath.resolve(fileName);
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////	
}
