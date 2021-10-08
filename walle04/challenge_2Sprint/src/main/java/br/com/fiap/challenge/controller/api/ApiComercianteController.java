package br.com.fiap.challenge.controller.api;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.challenge.model.Comerciante;
import br.com.fiap.challenge.repository.ComercianteRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/comerciante")
@Slf4j
public class ApiComercianteController {
	
	@Autowired
	private ComercianteRepository repository;
	
	@GetMapping
	@Cacheable("comerciantes")
	public Page<Comerciante> index(
			@RequestParam(required = false) String title,
			@PageableDefault(size = 20) Pageable pageable
			) {
		
		if (title == null)
			return repository.findAll(pageable);
		
		//TODO usar contains
		return repository.findByTitleLike("%" + title + "%", pageable);
	}
	
	@PostMapping
	@CacheEvict(value = "comerciantes", allEntries = true)
	public ResponseEntity<Comerciante> create(@RequestBody @Valid Comerciante comerciante, UriComponentsBuilder uriBuilder) {
		repository.save(comerciante);
		URI uri = uriBuilder
				.path("/api/comerciante/{id}")
				.buildAndExpand(comerciante.getId())
				.toUri();
		return ResponseEntity.created(uri).body(comerciante);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Comerciante> get(@PathVariable Long id) {
		return ResponseEntity.of(repository.findById(id));
	}
	
	@DeleteMapping("{id}")
	@CacheEvict(value = "comerciantes", allEntries = true)
	public ResponseEntity<Comerciante> delete(@PathVariable Long id){
		Optional<Comerciante> comerciante = repository.findById(id);
		
		if(comerciante.isEmpty()) 
			return ResponseEntity.notFound().build();
		
		repository.deleteById(id);
		
		return ResponseEntity.ok().build();
		
	}

	@PutMapping("{id}")
	@CacheEvict(value = "comerciantes", allEntries = true)
	public ResponseEntity<Comerciante> update(@RequestBody @Valid Comerciante newComerciante, @PathVariable Long id ) {
		Optional<Comerciante> optional = repository.findById(id);
		
		if(optional.isEmpty()) 
			return ResponseEntity.notFound().build();
		
		Comerciante comerciante = optional.get();
		
		comerciante.setTitle(newComerciante.getTitle());
		comerciante.setDescription(newComerciante.getDescription());
		
		
		repository.save(comerciante);
		
		log.info("Tarefa id=" + id +" alterada para " + comerciante.toString());

		return ResponseEntity.ok(comerciante);

		
	}
	
	
	
	
	
	
	
	
	
	

}
