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

@Entity
@Table(name = "product",schema = "shop")
public class ProductModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	@Column(name = "name_product")
	private String name_product;
	@Column(name = "section")
	private String section;
	@Column(name = "description")
	private String description;
	@Column(name = "quantity")
	private long quantity;
	@Column(name = "price")
	private long price;
	@Column(name = "logo")
	private String logo;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "section_id",nullable = false)
	private SectionModel sectionModel;
	
	public ProductModel() {
	}

	public ProductModel(long id, String name_product,String section, String description, int quantity, int price,
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

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
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
