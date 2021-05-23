package ru.example.demo.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVC_Config implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path logoUploadDir = Paths.get("./products-logos");
		String logoUploadPath = logoUploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/products-logos/**").addResourceLocations("file:/" + logoUploadPath + "/");
		
		
	}
	
}
