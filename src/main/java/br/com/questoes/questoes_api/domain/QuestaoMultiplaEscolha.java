package br.com.questoes.questoes_api.domain;

import br.com.questoes.questoes_api.enums.AlternativaEnum;
import br.com.questoes.questoes_api.enums.TipoQuestaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("MULTIPLA_ESCOLHA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoMultiplaEscolha extends Questao {

    @Column(name = "alternativa_a", nullable = false)
    private String alternativaA;

    @Column(name = "alternativa_b", nullable = false)
    private String alternativaB;

    @Column(name = "alternativa_c", nullable = false)
    private String alternativaC;

    @Column(name = "alternativa_d", nullable = false)
    private String alternativaD;

    @Column(name = "alternativa_e", nullable = false)
    private String alternativaE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlternativaEnum alternativaCorreta;

    {
        super.setTipo(TipoQuestaoEnum.MULTIPLA_ESCOLHA);
    }
}