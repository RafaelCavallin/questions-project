package br.com.questoes.questoes_api.dto.tentativa;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IniciarTentativaRequest {
    @NotNull
    private Long usuarioId;
    @NotNull
    private Long questaoId;
    private Long sessaoEstudoId;
    private Long provaId;

}
