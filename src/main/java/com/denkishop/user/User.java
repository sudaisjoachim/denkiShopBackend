package com.denkishop.user;


import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@DynamicInsert 
@DynamicUpdate 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;
	@NotBlank
	@Size(max = 100)
	@Email
	@Column(unique = true)
	private String email;
	private boolean active;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at;

    public User() {
    }

    public User(String username, String password, String role, @NotBlank @Size(max = 100) @Email String email,
			boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.active = active;
	}

	public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    

    public User(Long id, String username, String password, String role, @NotBlank @Size(max = 100) @Email String email,
			boolean active) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.active = active;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setRole(String role) {
        this.role = role;
    }
    

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	
}

