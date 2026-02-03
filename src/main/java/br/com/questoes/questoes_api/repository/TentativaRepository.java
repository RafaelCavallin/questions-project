package br.com.questoes.questoes_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.questoes.questoes_api.domain.Tentativa;

import java.util.List;

@Repository
public interface TentativaRepository extends JpaRepository<Tentativa, Long> {
    List<Tentativa> findByUsuarioId(Long usuarioId);
}
