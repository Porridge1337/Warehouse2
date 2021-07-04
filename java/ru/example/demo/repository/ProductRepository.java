package ru.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import ru.example.demo.model.ProductModel;

public interface ProductRepository extends PagingAndSortingRepository<ProductModel, Long>{

	@Query(value = "SELECT * FROM product  WHERE section_id = :id",
			nativeQuery = true)
	Page<ProductModel>findProductsModelBySectionId(@Param("id")long sectionId, Pageable pageable);
	
	@Query(value = "SELECT * FROM product  WHERE section_id = :id",
			nativeQuery = true)
	List<ProductModel>findProductsModelBySectionId(@Param("id")long sectionId);
	
	
}
