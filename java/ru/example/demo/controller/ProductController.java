package ru.example.demo.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import ru.example.demo.service.productModel.ProductServiceImpl;
import ru.example.demo.service.sectionModel.SectionServiceImpl;

@Controller
@RequestMapping("/section")
public class ProductController {

	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private SectionServiceImpl sectionService;
	
	
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
					
		productService.addProduct(productModel,multipartFile);
		calculateQuantities(productModel.getSectionModel().getId());
												
		return "redirect:/section/products";
	}
				
	@GetMapping("/{id_sec}/products")
	public String getProductsById(@PathVariable("id_sec")long id_sec,@ModelAttribute(name = "product") ProductModel productModel , Model model) {
		
		model.addAttribute("products", productService.findProductBySectionId(id_sec));
		model.addAttribute("product",productModel);
		model.addAttribute("sect",productService.findAllSections());
		
		return"products/product";
	}
		
	@GetMapping("/{id_sec}/products/{id}/update") 
	public String getProductUpdatePage(@PathVariable("id_sec")long id_sec, @PathVariable("id") long id,Model model) {
		
		if(productService.isExist(id)) {					
			model.addAttribute("update", productService.findProductById(id));
			model.addAttribute("sect",productService.findAllSections());
		return "products/productUpdate";
		}else return "redirect:/section/products";
						
	}
	
	@PatchMapping("/{id_sec}/products/{id}/update")
	public String updateProduct(@PathVariable("id_sec")long id_sec,@PathVariable("id")long id,
			@ModelAttribute(name = "update")ProductModel productModel,
			@RequestParam("fileImage")MultipartFile multipartFile) throws IOException {
				
		productService.updateProduct(productModel,multipartFile );
		
		return "redirect:/section/" + id_sec + "/products";
	}
	
	@DeleteMapping("/{id_sec}/products/{id}/delete")
	public String deleteProduct(@PathVariable("id_sec")long id_sec,@PathVariable("id")long id) {
						
		productService.deleteProduct(id);		
		calculateQuantities(id_sec);
				
		return"redirect:/section/products";
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	private void calculateQuantities(long section_id) { //высчитывает колличество продуктов в таблицу секции
		int ammountProductsModel = productService.findProductBySectionId(section_id).size();		
		SectionModel sectionModel = sectionService.findBySectionId(section_id);
		sectionModel.setQuantity(ammountProductsModel);
		sectionService.updateProduct(sectionModel);				
	}	
////////////////////////////////////////////////////////////////////////////////////////////////	
}
