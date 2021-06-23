package ru.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Table(name = "product",schema = "shop")
public class ProductModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	
	@Column(name = "name_product")
	@Size(min = 1,max = 45,message = "min symbols is 1, max 45 letters")
	private String name_product;
	
	@Column(name = "section")
	private String section;
	
	@Column(name = "description")
	@Size(min=1,max = 300, message = "description should not be blank or should not exceed 300 characters ")
	private String description;	

	@Size(max = 10,message = "max 10 number,should not be blank")
	@Pattern(regexp = "[0-9]+", message = "not be String or null")
	@Column(name = "quantity")
	private String quantity;
	
	@Size(max = 10,message = "max 10 number,should not be blank")
	@Pattern(regexp = "[0-9]+", message = "not be String or null")
	@Column(name = "price")
	private String price;
	
	@Column(name = "logo")
	private String logo;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "section_id",nullable = false)
	private SectionModel sectionModel;
	
	public ProductModel() {
	}

	public ProductModel(long id, String name_product,String section, String description, String quantity, String price,
			SectionModel sectionModel) {
		this.id = id;
		this.name_product = name_product;
		this.section=section;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.sectionModel = sectionModel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName_product() {
		return name_product;
	}

	public void setName_product(String name_product) {
		this.name_product = name_product;
	}
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public SectionModel getSectionModel() {
		return sectionModel;
	}

	public void setSectionModel(SectionModel sectionModel) {
		this.sectionModel = sectionModel;
	}
	
	
	@Transient
	public String getLogoImagePath() {
		if(logo==null || section == null || name_product == null) return null;
		
		return "/products-logos/"+section+"/"+name_product+"/"+logo;
	}

	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", name_product=" + name_product + ", section=" + section + ", description="
				+ description + ", quantity=" + quantity + ", price=" + price + ", sectionModel=" + sectionModel + "]";
	}
	
	
	
}
