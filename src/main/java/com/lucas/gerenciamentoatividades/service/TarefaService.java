package com.lucas.gerenciamentoatividades.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.gerenciamentoatividades.model.Tarefa;
import com.lucas.gerenciamentoatividades.repository.TarefaRepository;

@Service
public class TarefaService {
	
	@Autowired
	private TarefaRepository repository;
	
	@Transactional(readOnly = true)
	public List<Tarefa> buscar(Tarefa filtro) {
		Example example = Example.of( filtro, 
				ExampleMatcher.matching()
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(example);
	}
}
