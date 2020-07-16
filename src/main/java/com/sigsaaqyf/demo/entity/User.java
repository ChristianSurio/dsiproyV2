package com.sigsaaqyf.demo.entity;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class User implements Serializable{
    
    private static final long serialVersionUID = 61L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Column
	@NotBlank(message = "obligatorio")
	private String primerNombre;
	@Column
	private String segundoNombre;
	@Column
	@NotBlank(message = "obligatorio")
	private String primerApellido;
	@Column
	@NotBlank(message = "obligatorio")
	private String segundoApellido;

	@Column
	@Pattern(regexp = "^[a-z A-Z]{2}[0-9]{5}$", message = "carnet no válido")//patrón de carnet: 2 letras y 5 números
	private String carnet;

	@Column
	@NotBlank(message = "email obligatorio")
	@Email(message = "no parece un correo")
	private String email;

	@Column
	@NotBlank(message = "password obligatorio")
	private String password;

	@Transient
	private String confirmPassword;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean locked;
	
//Relacion muchos a muchos entre usuario y rol
	@ManyToOne(fetch = FetchType.LAZY)
	/*@JoinTable(
		name = "user_role",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))*/
	private Role roles;

	//getters y setters..........................
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}
	public void setRoles(Optional<Role> role){
		try {
			this.roles=role.get();
		} catch (Exception e) {
			System.out.println("No existe");
		}
		
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public User() {
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

}