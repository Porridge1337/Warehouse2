package ru.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "section", schema = "shop")
public class SectionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "section")	
	private String section;
	@Column(name = "quantity")	
	private int quantity;
	@Column(name = "description")
	private String description;
	@OneToMany(mappedBy = "sectionModel",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<ProductModel> productModel = new HashSet<ProductModel>();
	
	public SectionModel() {
	}		
	public SectionModel(long id, String section, int quantity, String description, Set<ProductModel> productModel) {
		this.id = id;
		this.section = section;
		this.quantity = quantity;
		this.description = description;
		this.productModel = productModel;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public Set<ProductModel> getProductModel() {
		return productModel;
	}
	public void setProductModel(Set<ProductModel> productModel) {
		this.productModel = productModel;
	}
	@Override
	public String toString() {
		return "SectionModel [id=" + id + ", section=" + section + ", quantity=" + quantity + ", description="
				+ description + ", productModel=" + productModel + "]";
	}
		
}
