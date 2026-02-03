package br.com.questoes.questoes_api.dto;

import java.util.Set;

import br.com.questoes.questoes_api.domain.ObjetivoProva;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvaCreateDTO {
    private String titulo;
    private String descricao;
    private ObjetivoProva objetivo;
    private Integer tempoEmMinutos;
    private Long bancaId;
    private Set<Long> disciplinasIds;
    private Set<Long> questoesIds;
}
