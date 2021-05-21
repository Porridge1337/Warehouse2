package ru.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ru.example.demo.model.UserModel;
import ru.example.demo.userDetailsImplementation.UserService;

@Controller
public class AdminPanelController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/adminPage")
	public String getAdminPage(Model model) {
		
		model.addAttribute("users",userService.getAllUsers());
		
		return"admin/adminPanel";
	}
	
	@GetMapping("/adminPage/update/{id}")
	public String getUserUpdatingPage(@PathVariable("id")long id, Model model) {		
		
		model.addAttribute("user", userService.getUserModel(id));
		model.addAttribute("roles", userService.getRoles());
		
		return "admin/userPanel";
	}
	
	@PatchMapping("/adminPage/update")
	public String updateUser(@ModelAttribute(name = "user")UserModel userModel) {
		
		userService.update(userModel);// тут
		return"redirect:/adminPage";
	}
	
	@DeleteMapping("/adminPage/delete/{id}")
	public String deletUser(@PathVariable("id")long id) {
		
		userService.deleteUser(id);
		return "redirect:/adminPage";
	}
	
}
