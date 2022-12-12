package com.lucas.gerenciamentoatividades.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.gerenciamentoatividades.exceptions.RegradeNegociosException;
import com.lucas.gerenciamentoatividades.model.Tarefa;
import com.lucas.gerenciamentoatividades.model.Usuario;
import com.lucas.gerenciamentoatividades.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository repository;

	public Usuario autenticar(String email, String senha) {

		Optional<Usuario> usuario = repository.findByEmail(email);

		if (!usuario.isPresent())

			throw new RegradeNegociosException("Usuario não encontrado.");

		if (!usuario.get().getSenha().equals(senha))
			throw new RegradeNegociosException("Senha incorreta.");

		return usuario.get();

	}
	
	
	@Transactional(readOnly = true)
	public List<Usuario> buscar(Usuario filtro) {
		Example example = Example.of( filtro, 
				ExampleMatcher.matching()
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(example);
	}
}
