package ru.example.demo.controller;

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
	public String addProduct(@ModelAttribute("product")ProductModel productModel) { 
		
		productService.addProduct(productModel);
		calculateQuantities(sectionService.findBySection(productModel.getSection()).getId());
		
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
	
	@GetMapping("/products/update/{id}") // добавить пост
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
	private void calculateQuantities(long section_id) {
		int ammountProductsModel =	productService.findProductBySectionId(section_id).size();		
		SectionModel sectionModel = sectionService.findBySectionId(section_id);
		sectionModel.setQuantity(ammountProductsModel);
		sectionService.updateProduct(sectionModel);				
	}
////////////////////////////////////////////////////////////////////////////////////////////////	
}
