package br.com.questoes.questoes_api.dto.sessaoestudo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SessaoEstudoCreateDTO(@NotNull Long usuarioId) {}
