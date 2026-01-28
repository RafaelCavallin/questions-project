package br.com.questoes.questoes_api.repository.disciplina;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.questoes.questoes_api.domain.Disciplina;

import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    Optional<Disciplina> findByNomeIgnoreCase(String nome);
}
