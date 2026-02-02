package br.com.questoes.questoes_api.repository;

import br.com.questoes.questoes_api.model.Prova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {
    @Query("SELECT p FROM Prova p WHERE p.ativa = true")
    List<Prova> findAllAtivas();
}
