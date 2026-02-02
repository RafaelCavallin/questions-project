package br.com.questoes.questoes_api.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvaUpdateDTO {
    private String titulo;
    private Long bancaId;
    private String descricao;
    private Integer tempoEmMinutos;
    private Boolean ativa;
    private Set<Long> disciplinasIds;
}
