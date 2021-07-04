package ru.example.demo.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	private boolean isPageBySectionIdProducts;
	
	@GetMapping("/products")
	public String productsPage(@ModelAttribute(name = "product") ProductModel productModel ,Model model) {
	
		return getProductsPage(productModel, 1,"nameproduct","unsorted",model );
	}
	@GetMapping("/products/{pageNumber}")
	public String getProductsPage(@ModelAttribute(name = "product") ProductModel productModel ,
			@PathVariable("pageNumber")int currentPage,
			@RequestParam(required = false, name = "sortField")String sortField,
			@RequestParam(required = false, name = "sortDir")String sortDir,
			Model model) {
			
		Page <ProductModel> page = productService.findAllProducts(currentPage,sortField,sortDir );
		long totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems",totalItems);
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("product",productModel);		
		model.addAttribute("products",page.getContent());				
		model.addAttribute("sect",productService.findAllSections());
		
		isPageBySectionIdProducts=false;
		return "products/products";
	}
	
	@PostMapping("/products/{pageNumber}/add")
	public String addProduct(@Valid @ModelAttribute("product")ProductModel productModel,
			BindingResult br,
			@RequestParam("fileImage")MultipartFile multipartFile,
			@PathVariable("pageNumber")int currentPage,
			Model model) throws IOException { 
						
		if(br.hasErrors()) {
			Page <ProductModel> page = productService.findAllProducts(currentPage,"nameproduct","unsorted");
			long totalPages = page.getTotalPages();
			long totalItems = page.getTotalElements();
			
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalItems",totalItems);
			
			model.addAttribute("sortField","nameproduct");
			model.addAttribute("sortDir","unsorted");
			model.addAttribute("reverseSortDir","asc");
			
			model.addAttribute("product",productModel);		
			model.addAttribute("products",page.getContent());				
			model.addAttribute("sect",productService.findAllSections());
			
			return "products/products";
		}
		
		productService.addProduct(productModel,multipartFile);
		calculateQuantities(productModel.getSectionModel().getId());
												
		return "redirect:/section/products";
	}
	
	@GetMapping("/{id_sec}/products")
	public String productPageBySecId(@PathVariable("id_sec")long id_sec,
			@ModelAttribute(name = "product") ProductModel productModel, 
			Model model) {
			
		return getProductPageBySecId(productModel,id_sec,1,"nameproduct","unsorted", model );	
	}
	
	@GetMapping("/{id_sec}/products/{pageNumber}")
	public String getProductPageBySecId(@ModelAttribute(name = "product") ProductModel productModel,
			@PathVariable("id_sec")long id_sec,
			@PathVariable(name = "pageNumber")int currentPage,
			@RequestParam(required = false, name = "sortField")String sortField,
			@RequestParam(required = false, name = "sortDir")String sortDir, 
			Model model) {
		Page <ProductModel> page = productService.findProductBySectionId(id_sec,currentPage,sortField,sortDir );
		long totalPages = page.getTotalPages();
		long totalElements = page.getTotalElements();
		
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalElements", totalElements);
		model.addAttribute("currentPage",currentPage);
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("product",productModel);
		model.addAttribute("products", page.getContent());		
		model.addAttribute("sect",productService.findAllSections());
		model.addAttribute("id_sec", id_sec); 
										
		isPageBySectionIdProducts=true;
		return"products/productsBySectionId";
	}
	@PostMapping("/{id_sec}/products/{pageNumber}")
	public String addProductBySecId(@Valid@ModelAttribute(name = "product") ProductModel productModel,			
			BindingResult br,
			@PathVariable("id_sec")long id_sec,
			@PathVariable(name = "pageNumber")int currentPage,
			@RequestParam("fileImage")MultipartFile multipartFile, 
			Model model) {
		
		if(br.hasErrors()) {
			Page <ProductModel> page = productService.findProductBySectionId(id_sec,currentPage,"nameproduct","unsorted");
			long totalPages = page.getTotalPages();
			long totalElements = page.getTotalElements();
			
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalElements", totalElements);
			model.addAttribute("currentPage",currentPage);
			
			model.addAttribute("product",productModel);
			model.addAttribute("products", page.getContent());		
			model.addAttribute("sect",productService.findAllSections());
			model.addAttribute("id_sec", id_sec); 
			return "products/productsBySectionId";
		}
		
		productService.addProduct(productModel,multipartFile);
		calculateQuantities(productModel.getSectionModel().getId());
												
		return "redirect:/section/"+id_sec+"/products";
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
			@RequestParam("fileImage")MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {
			
		if(multipartFile.getSize() > 3145728) {
			redirectAttributes.addFlashAttribute("message",multipartFile.getOriginalFilename() + " Requires less than 3 MB size");			
			return "redirect:/section/"+id_sec+"/products/"+id+"/update";
		}
				
		productService.updateProduct(productModel,multipartFile );
		
		return "redirect:/section/" + id_sec + "/products";
	}
	
	@DeleteMapping("/{id_sec}/products/{id}/delete")
	public String deleteProduct(@PathVariable("id_sec")long id_sec,@PathVariable("id")long id) {
						
		productService.deleteProduct(id);		
		calculateQuantities(id_sec);
				
		if(!isPageBySectionIdProducts) { 
			return"redirect:/section/products";
		}else return "redirect:/section/{id_sec}/products";
		
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	private void calculateQuantities(long section_id) { //высчитывает и записывает колличество продуктов в таблицу секции
		int ammountProductsModel = productService.getSizeOfProductsModelBySectionId(section_id);
		SectionModel sectionModel = sectionService.findBySectionId(section_id);
		sectionModel.setQuantity(ammountProductsModel);
		sectionService.updateProduct(sectionModel);				
	}	
////////////////////////////////////////////////////////////////////////////////////////////////	
}
