package com.lucas.gerenciamentoatividades.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TarefaDTO {

	private Long id;
	private String descricao;
	private LocalDate dataEntrega;
	private Long usuario;
}
