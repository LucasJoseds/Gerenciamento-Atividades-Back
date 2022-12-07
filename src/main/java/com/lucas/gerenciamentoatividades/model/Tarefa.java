package com.lucas.gerenciamentoatividades.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricão;
	
	@Column(name="data_entrega")
	private LocalDate dataEntrega;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

}
