package br.com.questoes.questoes_api.dto.disciplina;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DisciplinaRequestDTO(

        @NotBlank
        @Size(max = 100)
        String nome,

        @Size(max = 255)
        String descricao
) {}
