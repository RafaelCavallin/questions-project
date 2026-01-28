package br.com.questoes.questoes_api.repository;

import br.com.questoes.questoes_api.domain.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {
    List<Assunto> findByDisciplinaIdAndAtivoTrue(Long disciplinaId);
    List<Assunto> findByAtivoTrue();

        @Query(value = "SELECT a FROM Assunto a JOIN FETCH a.disciplina WHERE a.ativo = true",
            countQuery = "SELECT COUNT(a) FROM Assunto a WHERE a.ativo = true")
        Page<Assunto> findByAtivoTrueWithDisciplina(Pageable pageable);

    @Query("SELECT a FROM Assunto a JOIN FETCH a.disciplina WHERE a.id = :id AND a.ativo = true")
    Optional<Assunto> findByIdWithDisciplina(Long id);
}
