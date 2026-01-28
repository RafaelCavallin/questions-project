package br.com.questoes.questoes_api.dto.assunto;

import java.time.LocalDateTime;

public record AssuntoResponseDTO(
    Long id,
    String nome,
    String descricao,
    Long disciplinaId,
    String disciplinaNome,
    Boolean ativo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
