package br.com.fiap.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.challenge.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
