package br.com.questoes.questoes_api.repository;

import br.com.questoes.questoes_api.domain.SessaoEstudo;
import br.com.questoes.questoes_api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudo, Long> {
    List<SessaoEstudo> findByUsuario(Usuario usuario);
}
