package br.com.questoes.questoes_api.dto.role;

public record RoleCreateRequestDto(
    String nome,
    Boolean ativo
) {}
