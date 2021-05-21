package ru.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.example.demo.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long>{

	@Query(value = "SELECT * FROM product  WHERE section_id = :id",
			nativeQuery = true)
	List<ProductModel>findProductsModelBySectionId(@Param("id")long sectionId);
	
}
