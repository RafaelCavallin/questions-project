package br.com.questoes.questoes_api.repository;

import br.com.questoes.questoes_api.domain.Banca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface BancaRepository extends JpaRepository<Banca, Long> {
    Optional<Banca> findByNome(String nome);
    Page<Banca> findAll(Pageable pageable);
}
