package com.lucas.gerenciamentoatividades.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.gerenciamentoatividades.exceptions.RegradeNegociosException;
import com.lucas.gerenciamentoatividades.model.Tarefa;
import com.lucas.gerenciamentoatividades.model.Usuario;
import com.lucas.gerenciamentoatividades.repository.UsuarioRepository;
import com.lucas.gerenciamentoatividades.service.UsuarioService;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioService service;

	@PostMapping("/cadastrar")
	public ResponseEntity cadastrar(@RequestBody Usuario usuario) {

		validarEmail(usuario.getEmail());

		try {
			repository.save(usuario);
			return new ResponseEntity<Usuario>(HttpStatus.CREATED);

		} catch (RegradeNegociosException e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("{id}")
	public ResponseEntity aterar(@RequestBody Usuario usuario, @PathVariable Long id) {

		Usuario usuarioid = repository.findById(id).get();

		usuarioid.setNome(usuario.getNome());
		usuarioid.setEmail(usuario.getEmail());

		try {
			repository.save(usuarioid);
			return ResponseEntity.ok(usuarioid);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable Long id) {

		Usuario usuario = repository.findById(id).get();
		repository.delete(usuario);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/listar")
	public List<Usuario> listarTodos() {

		return repository.findAll();

	}
	@GetMapping("{id}")
	public ResponseEntity obterUsuario(@PathVariable Long id) {
	
		try {
			Usuario usuario= repository.findById(id).get();
			return new ResponseEntity(usuario, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}

	public void validarEmail(String email) {

		boolean resultado = repository.existsByEmail(email);

		if (resultado)
			throw new RegradeNegociosException("Já existe um usuário cadastrado com este email.");

	}

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody Usuario usuario) {

		try {
			Usuario usuarioAutenticado = service.autenticar(usuario.getEmail(), usuario.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (RegradeNegociosException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "nome", required = false) String nome) {

		Usuario filtro = new Usuario();
		filtro.setNome(nome);

		List<Usuario> usuario = service.buscar(filtro);
		return ResponseEntity.ok(usuario);
	}

}
