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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;
	@Column(name = "username")
	@Size(min = 3, max = 25, message = "Size must be between 3 and 25")
	private String username;
	@Column(name = "password")
	@NotBlank(message = "password shouldnt be blank")
	@Size(min = 8, max = 100, message = "mininum 8 symbols")
	private String password;
	@Column(name = "actives")
	private boolean actives;
	@ManyToMany(cascade ={
		CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.REFRESH,
        CascadeType.PERSIST
	},
	fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")			
			)
	private Set<Roles>role = new HashSet<>();
		
	
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActives() {
		return actives;
	}

	public void setActives(boolean actives) {
		this.actives = actives;
	}

	public Set <Roles> getRole() {
		return role;
	}

	public void setRole(Set <Roles> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserModel [user_id=" + user_id + ", username=" + username + ", password=" + password + ", actives="
				+ actives + ", role=" + role + "]";
	}
	

	
	
	
	
	
	
}
