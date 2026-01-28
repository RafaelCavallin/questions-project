package br.com.questoes.questoes_api.dto.disciplina;

public record DisciplinaResponseDTO(
        Long id,
        String nome,
        String descricao,
        Boolean ativa
) {}
