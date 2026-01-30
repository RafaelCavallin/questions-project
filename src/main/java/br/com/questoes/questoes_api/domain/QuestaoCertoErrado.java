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
@DiscriminatorValue("CERTO_ERRADO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoCertoErrado extends Questao {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlternativaEnum alternativaCorreta;

    {
        super.setTipo(TipoQuestaoEnum.CERTO_ERRADO);
    }
}