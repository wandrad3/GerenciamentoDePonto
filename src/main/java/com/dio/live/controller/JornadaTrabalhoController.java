package com.dio.live.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dio.live.model.JornadaTrabalho;
import com.dio.live.service.JornadaTrabalhoService;

@RestController
@RequestMapping("/jornada")

public class JornadaTrabalhoController {

	@Autowired
	private JornadaTrabalhoService jornadaTrabalhoService;
	
	@GetMapping("/paged")
	public ResponseEntity<Page<JornadaTrabalho>> findAllPaged(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy

	) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<JornadaTrabalho> list = jornadaTrabalhoService.findAllPaged(pageRequest);

		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping
	public ResponseEntity<List<JornadaTrabalho>> findAll() {


		List<JornadaTrabalho> list = jornadaTrabalhoService.findAll();

		return ResponseEntity.ok().body(list);
	}

	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<JornadaTrabalho> findById(@PathVariable Long id){
		JornadaTrabalho jornadaTrabalho = jornadaTrabalhoService.findById(id);
		return ResponseEntity.ok().body(jornadaTrabalho);
	}
	
	@PostMapping
	public ResponseEntity<JornadaTrabalho> insert(@RequestBody JornadaTrabalho jornadaTrabalho){
		
		jornadaTrabalho =  jornadaTrabalhoService.insert(jornadaTrabalho);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(jornadaTrabalho.getId())
				.toUri();
		return ResponseEntity.created(uri).body(jornadaTrabalho);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<JornadaTrabalho> update(@PathVariable Long id,@RequestBody JornadaTrabalho JornadaTrabalho){
		
		JornadaTrabalho =  jornadaTrabalhoService.update(id, JornadaTrabalho);
		return ResponseEntity.ok().body(JornadaTrabalho);
	}
	
	
	
	@DeleteMapping (value="/{id}")
	public ResponseEntity<JornadaTrabalho> delete(@PathVariable Long id){
		jornadaTrabalhoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}