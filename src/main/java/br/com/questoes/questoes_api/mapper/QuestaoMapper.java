package br.com.questoes.questoes_api.mapper;

import br.com.questoes.questoes_api.domain.Questao;
import br.com.questoes.questoes_api.domain.QuestaoMultiplaEscolha;
import br.com.questoes.questoes_api.domain.QuestaoCertoErrado;
import br.com.questoes.questoes_api.dto.questao.QuestaoRequestDTO;
import br.com.questoes.questoes_api.dto.questao.QuestaoResponseDTO;
import br.com.questoes.questoes_api.enums.TipoQuestaoEnum;
import org.springframework.stereotype.Component;

@Component
public class QuestaoMapper {
    public Questao toEntity(QuestaoRequestDTO dto) {
            if (dto.getTipoQuestao() == TipoQuestaoEnum.MULTIPLA_ESCOLHA) {
                QuestaoMultiplaEscolha q = new QuestaoMultiplaEscolha();
                q.setEnunciado(dto.getEnunciado());
                q.setAlternativaA(dto.getAlternativaA());
                q.setAlternativaB(dto.getAlternativaB());
                q.setAlternativaC(dto.getAlternativaC());
                q.setAlternativaD(dto.getAlternativaD());
                q.setAlternativaE(dto.getAlternativaE());
                q.setAlternativaCorreta(dto.getAlternativaCorreta());
                q.setNivelDificuldade(dto.getNivelDificuldade());
                // Relacionamentos setados no service
                return q;
            } else if (dto.getTipoQuestao() == TipoQuestaoEnum.CERTO_ERRADO) {
                QuestaoCertoErrado q = new QuestaoCertoErrado();
                q.setEnunciado(dto.getEnunciado());
                // Para certo/errado, alternativaCorretaCerto: true = Certo (C), false = Errado (E)
                // Usar alternativaCorreta == C (Certo) ou E (Errado) por convenção
                if (dto.getAlternativaCorreta() != null) {
                    q.setAlternativaCorreta(dto.getAlternativaCorreta());
                }
                q.setNivelDificuldade(dto.getNivelDificuldade());
                // Relacionamentos setados no service
                return q;
            } else {
                throw new IllegalArgumentException("Tipo de questão não suportado");
            }
    }
    public QuestaoResponseDTO toDTO(Questao q) {
        QuestaoResponseDTO dto = new QuestaoResponseDTO();
        dto.setId(q.getId());
        dto.setEnunciado(q.getEnunciado());
        dto.setNivelDificuldade(q.getNivelDificuldade());
        dto.setDisciplina(q.getDisciplina() != null ? q.getDisciplina().getNome() : null);
        dto.setAssunto(q.getAssunto() != null ? q.getAssunto().getNome() : null);
        dto.setBanca(q.getBanca() != null ? q.getBanca().getNome() : null);
        dto.setAtivo(q.getAtivo());
        dto.setCreatedAt(q.getCreatedAt());
        dto.setUpdatedAt(q.getUpdatedAt());
        if (q instanceof QuestaoMultiplaEscolha) {
            QuestaoMultiplaEscolha qm = (QuestaoMultiplaEscolha) q;
            dto.setAlternativaA(qm.getAlternativaA());
            dto.setAlternativaB(qm.getAlternativaB());
            dto.setAlternativaC(qm.getAlternativaC());
            dto.setAlternativaD(qm.getAlternativaD());
            dto.setAlternativaE(qm.getAlternativaE());
            dto.setAlternativaCorreta(qm.getAlternativaCorreta());
            dto.setTipoQuestao(TipoQuestaoEnum.MULTIPLA_ESCOLHA);
        } else if (q instanceof QuestaoCertoErrado) {
            QuestaoCertoErrado qc = (QuestaoCertoErrado) q;
            dto.setAlternativaA("Certo");
            dto.setAlternativaB("Errado");
            dto.setAlternativaC(null);
            dto.setAlternativaD(null);
            dto.setAlternativaE(null);
            dto.setAlternativaCorreta(qc.getAlternativaCorreta());
            dto.setTipoQuestao(TipoQuestaoEnum.CERTO_ERRADO);
        }
        return dto;
    }
}
