package br.com.questoes.questoes_api.dto.usuario;

import java.util.Set;

public record UsuarioResponse (Long id,
    String nome,
     String email,
    Boolean ativo,
    Set<String> roles) {
}
