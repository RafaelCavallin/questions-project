package br.com.questoes.questoes_api.dto.tentativa;

import br.com.questoes.questoes_api.enums.TentativaStatus;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TentativaResponse {
    private Long id;
    private LocalDateTime criadaEm;
    private LocalDateTime finalizadaEm;
    private Integer tempoGastoEmSegundos;
    private TentativaStatus status;
    private Boolean acertou;
    private Long usuarioId;
    private Long questaoId;
    private Long sessaoEstudoId;
    private Long provaId;

}
