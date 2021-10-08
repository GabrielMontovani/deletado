package br.com.fiap.challenge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.challenge.model.Comerciante;

public interface ComercianteRepository extends JpaRepository<Comerciante, Long> {
	
	Page<Comerciante> findByTitleLike(String title, Pageable pageable);

}
