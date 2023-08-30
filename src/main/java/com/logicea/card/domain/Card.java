package com.logicea.card.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table
public class Card {
	
	@Id
	@NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@Column
	private LocalDateTime datecreated; //LocalDateTime
	
	@Column
	private LocalDateTime datemodified;
	
	@Column
	private String name;
	
	@Column
	private String color;
	
	@Column
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	/*
	 * the card HAS TO have an owner
	 * this represents a one to many relationship howver we keep it disociatted given
	 * a user can have an infite number of cards and it affects performance having the relationship
	 * so tightly couples
	 * 
	 * We can have a many to one  @ManyToOne
	 */
	@NotNull
	@Column
	private Long userId;
	
	
	
}
