package br.com.questoes.questoes_api.dto.assunto;

public record AssuntoRequestDTO(
    String nome,
    String descricao,
    Boolean ativo,
    Long disciplinaId
) {}
