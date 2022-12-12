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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.gerenciamentoatividades.dto.TarefaDTO;
import com.lucas.gerenciamentoatividades.model.Tarefa;
import com.lucas.gerenciamentoatividades.model.Usuario;
import com.lucas.gerenciamentoatividades.repository.TarefaRepository;
import com.lucas.gerenciamentoatividades.repository.UsuarioRepository;
import com.lucas.gerenciamentoatividades.service.TarefaService;

@RestController
@RequestMapping(value="/api/atividades")
public class TarefaController {

	@Autowired
	TarefaRepository repository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TarefaService service;
	
	
	@PostMapping("/cadastrar")
	public ResponseEntity cadastrar(@RequestBody TarefaDTO dto) {
		
		
		Long id = dto.getUsuario();
		Usuario usuario = usuarioRepository.findById(id).get();
		
		Tarefa tarefa= new Tarefa();
		tarefa.setDescricao(dto.getDescricao());
		tarefa.setDataEntrega(dto.getDataEntrega());
		tarefa.setUsuario(usuario);
		
		try {
			repository.save(tarefa);
			return new ResponseEntity<Tarefa>(HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	@GetMapping("/listar")
	public List<Tarefa> listarTarefas(){
		
		return repository.findAll();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable Long id) {

		Tarefa tarefa = repository.findById(id).get();
		repository.delete(tarefa);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping
	public ResponseEntity buscar(@RequestParam (value ="descricao" , required = false)String descricao, 
			@RequestParam(value ="data" , required = false) LocalDate data,
			@RequestParam("usuario") Long idUsuario) {
		
		Tarefa filtro = new Tarefa();
		filtro.setDescricao(descricao);
		filtro.setDataEntrega(data);

		Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
		
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o Id informado.");
		}else {
			filtro.setUsuario(usuario.get());
		}
		
		List<Tarefa> lancamentos = service.buscar(filtro);
		return ResponseEntity.ok(lancamentos);
	}
}
