package br.com.questoes.questoes_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.questoes.questoes_api.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}

