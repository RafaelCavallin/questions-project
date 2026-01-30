package br.com.questoes.questoes_api.repository;

import br.com.questoes.questoes_api.domain.Questao;
import br.com.questoes.questoes_api.enums.NivelDificuldadeEnum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {
    @Query("SELECT q FROM Questao q WHERE (:disciplinaId IS NULL OR q.disciplina.id = :disciplinaId) " +
           "AND (:bancaId IS NULL OR q.banca.id = :bancaId) " +
           "AND (:nivel IS NULL OR q.nivelDificuldade = :nivel) " +
           "AND q.ativo = true")
    Page<Questao> findByFilters(@Param("disciplinaId") Long disciplinaId,
                               @Param("bancaId") Long bancaId,
                               @Param("nivel") NivelDificuldadeEnum nivel,
                               Pageable pageable);
}
