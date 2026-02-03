package br.com.questoes.questoes_api.dto.tentativa;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponderTentativaRequest {
    @NotNull
    private Long tentativaId;
    @NotNull
    private Boolean acertou;
    // Futuramente: resposta detalhada

}
