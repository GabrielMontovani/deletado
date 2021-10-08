package br.com.fiap.challenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Comerciante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "{comerciante.title.blank}")
	private String title;
	
	@Size(min=15, message = "A descrição deve ter pelo menos 15 caracteres")
	private String description;
	
	

}
