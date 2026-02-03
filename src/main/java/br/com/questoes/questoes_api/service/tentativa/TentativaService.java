package br.com.questoes.questoes_api.service.tentativa;

import br.com.questoes.questoes_api.dto.tentativa.IniciarTentativaRequest;
import br.com.questoes.questoes_api.dto.tentativa.ResponderTentativaRequest;
import br.com.questoes.questoes_api.dto.tentativa.TentativaResponse;
import br.com.questoes.questoes_api.enums.TentativaStatus;
import br.com.questoes.questoes_api.exception.BusinessException;
import br.com.questoes.questoes_api.domain.Usuario;
import br.com.questoes.questoes_api.domain.Prova;
import br.com.questoes.questoes_api.domain.Questao;
import br.com.questoes.questoes_api.domain.SessaoEstudo;
import br.com.questoes.questoes_api.domain.Tentativa;
import br.com.questoes.questoes_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TentativaService {
    @Autowired
    private TentativaRepository tentativaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private QuestaoRepository questaoRepository;
    @Autowired
    private SessaoEstudoRepository sessaoEstudoRepository;
    @Autowired
    private ProvaRepository provaRepository;

    @Transactional
    public TentativaResponse iniciarTentativa(IniciarTentativaRequest request) {
        if ((request.getSessaoEstudoId() == null && request.getProvaId() == null) ||
            (request.getSessaoEstudoId() != null && request.getProvaId() != null)) {
            throw new BusinessException("Tentativa deve estar vinculada a SessaoEstudo OU Prova, nunca ambos nulos ou ambos preenchidos.");
        }
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));
        Questao questao = questaoRepository.findById(request.getQuestaoId())
                .orElseThrow(() -> new BusinessException("Questão não encontrada"));
        SessaoEstudo sessaoEstudo = null;
        if (request.getSessaoEstudoId() != null) {
            sessaoEstudo = sessaoEstudoRepository.findById(request.getSessaoEstudoId())
                    .orElseThrow(() -> new BusinessException("Sessão de estudo não encontrada"));
        }
        Prova prova = null;
        if (request.getProvaId() != null) {
            prova = provaRepository.findById(request.getProvaId())
                    .orElseThrow(() -> new BusinessException("Prova não encontrada"));
        }
        Tentativa tentativa = new Tentativa();
        tentativa.setUsuario(usuario);
        tentativa.setQuestao(questao);
        tentativa.setSessaoEstudo(sessaoEstudo);
        tentativa.setProva(prova);
        tentativa.setCriadaEm(LocalDateTime.now());
        tentativa.setStatus(TentativaStatus.INICIADA);
        tentativa = tentativaRepository.save(tentativa);
        return toResponse(tentativa);
    }

    @Transactional
    public TentativaResponse responderTentativa(Long tentativaId, ResponderTentativaRequest request) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId)
                .orElseThrow(() -> new BusinessException("Tentativa não encontrada"));
        if (tentativa.getStatus() != TentativaStatus.INICIADA) {
            throw new BusinessException("Só é possível responder tentativas com status INICIADA");
        }
        tentativa.setAcertou(request.getAcertou());
        tentativa.setStatus(TentativaStatus.RESPONDIDA);
        tentativaRepository.save(tentativa);
        return toResponse(tentativa);
    }

    @Transactional
    public TentativaResponse finalizarTentativa(Long tentativaId) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId)
                .orElseThrow(() -> new BusinessException("Tentativa não encontrada"));
        if (tentativa.getStatus() != TentativaStatus.RESPONDIDA) {
            throw new BusinessException("Só é possível finalizar tentativas com status RESPONDIDA");
        }
        tentativa.setFinalizadaEm(LocalDateTime.now());
        if (tentativa.getCriadaEm() != null && tentativa.getFinalizadaEm() != null) {
            tentativa.setTempoGastoEmSegundos((int) java.time.Duration.between(
                tentativa.getCriadaEm(), tentativa.getFinalizadaEm()).getSeconds());
        }
        tentativa.setStatus(TentativaStatus.FINALIZADA);
        tentativaRepository.save(tentativa);
        return toResponse(tentativa);
    }

    @Transactional(readOnly = true)
    public TentativaResponse buscarTentativa(Long id) {
        Tentativa tentativa = tentativaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tentativa não encontrada"));
        return toResponse(tentativa);
    }

    @Transactional(readOnly = true)
    public List<TentativaResponse> listarTentativasPorUsuario(Long usuarioId) {
        List<Tentativa> tentativas = tentativaRepository.findByUsuarioId(usuarioId);
        return tentativas.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private TentativaResponse toResponse(Tentativa tentativa) {
        TentativaResponse dto = new TentativaResponse();
        dto.setId(tentativa.getId());
        dto.setCriadaEm(tentativa.getCriadaEm());
        dto.setFinalizadaEm(tentativa.getFinalizadaEm());
        dto.setTempoGastoEmSegundos(tentativa.getTempoGastoEmSegundos());
        dto.setStatus(tentativa.getStatus());
        dto.setAcertou(tentativa.getAcertou());
        dto.setUsuarioId(tentativa.getUsuario() != null ? tentativa.getUsuario().getId() : null);
        dto.setQuestaoId(tentativa.getQuestao() != null ? tentativa.getQuestao().getId() : null);
        dto.setSessaoEstudoId(tentativa.getSessaoEstudo() != null ? tentativa.getSessaoEstudo().getId() : null);
        dto.setProvaId(tentativa.getProva() != null ? tentativa.getProva().getId() : null);
        return dto;
    }
}
