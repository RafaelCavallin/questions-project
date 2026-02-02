package br.com.questoes.questoes_api.dto;

import br.com.questoes.questoes_api.model.ObjetivoProva;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private ObjetivoProva objetivo;
    private Integer tempoEmMinutos;
    private Boolean ativa;
    private LocalDateTime criadaEm;
    private LocalDateTime atualizadaEm;
    private Long bancaId;
    private Set<Long> disciplinasIds;
    private Set<Long> questoesIds;
}
