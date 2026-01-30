package br.com.questoes.questoes_api.dto.questao;

import br.com.questoes.questoes_api.enums.AlternativaEnum;
import br.com.questoes.questoes_api.enums.NivelDificuldadeEnum;
import br.com.questoes.questoes_api.enums.TipoQuestaoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestaoRequestDTO {
    private String enunciado;
    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String alternativaE;
    private TipoQuestaoEnum tipoQuestao;
    private AlternativaEnum alternativaCorreta;
    private NivelDificuldadeEnum nivelDificuldade;
    private Long disciplinaId;
    private Long assuntoId;
    private Long bancaId;
}
