package com.lucas.gerenciamentoatividades.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.gerenciamentoatividades.model.Usuario;
import com.lucas.gerenciamentoatividades.repository.UsuarioRepository;


@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@PostMapping("/cadastrar")
	public ResponseEntity cadastrar(@RequestBody Usuario usuario) {

		try {
			repository.save(usuario);
			return new ResponseEntity<Usuario>(HttpStatus.CREATED);

		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	
	@GetMapping("/listar")
	public List<Usuario> listarTodos(){
		
		return repository.findAll();
		
	}
}
