package com.logicea.security.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author infilitry
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
	uniqueConstraints = {
	    @UniqueConstraint(columnNames = "email")
})
public class User {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column
	private LocalDateTime datecreated;

	@NotBlank
	@Email
	@Column(unique=true)
	private String email; //this has to be email

	@NotBlank
	@Column
	private String password;


	/*
	 * users can have many roles can be one to many
	 * 
	 * role can have many permissions
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
}
