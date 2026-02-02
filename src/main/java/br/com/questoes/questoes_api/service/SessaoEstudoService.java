package br.com.questoes.questoes_api.service;

import br.com.questoes.questoes_api.domain.SessaoEstudo;
import br.com.questoes.questoes_api.domain.Usuario;
import br.com.questoes.questoes_api.dto.sessaoestudo.SessaoEstudoResponseDTO;
import br.com.questoes.questoes_api.enums.SessaoEstudoStatus;
import br.com.questoes.questoes_api.exception.NotFoundException;
import br.com.questoes.questoes_api.exception.BusinessException;
import br.com.questoes.questoes_api.repository.SessaoEstudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessaoEstudoService {
    @Autowired
    private SessaoEstudoRepository sessaoEstudoRepository;

    @Transactional
    public SessaoEstudoResponseDTO criarSessao(Usuario usuario) {
        SessaoEstudo sessao = new SessaoEstudo();
        sessao.setUsuario(usuario);
        sessao.setStartedAt(LocalDateTime.now());
        sessao.setStatus(SessaoEstudoStatus.ATIVA);
        sessao = sessaoEstudoRepository.save(sessao);
        return toDTO(sessao);
    }

    @Transactional
    public SessaoEstudoResponseDTO finalizarSessao(Long sessaoId, Usuario usuario) {
        SessaoEstudo sessao = sessaoEstudoRepository.findById(sessaoId)
            .orElseThrow(() -> new NotFoundException("Sessão não encontrada"));
        if (!sessao.getUsuario().getId().equals(usuario.getId())) {
            throw new BusinessException("Acesso negado à sessão de outro usuário");
        }
        if (sessao.getStatus() == SessaoEstudoStatus.FINALIZADA) {
            throw new BusinessException("Sessão já finalizada");
        }
        sessao.setEndedAt(LocalDateTime.now());
        sessao.setStatus(SessaoEstudoStatus.FINALIZADA);
        sessao = sessaoEstudoRepository.save(sessao);
        return toDTO(sessao);
    }

    @Transactional(readOnly = true)
    public SessaoEstudoResponseDTO findById(Long id, Usuario usuario) {
        SessaoEstudo sessao = sessaoEstudoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Sessão não encontrada"));
        if (!sessao.getUsuario().getId().equals(usuario.getId())) {
            throw new BusinessException("Acesso negado à sessão de outro usuário");
        }
        return toDTO(sessao);
    }

    @Transactional(readOnly = true)
    public List<SessaoEstudoResponseDTO> listByUser(Usuario usuario) {
        return sessaoEstudoRepository.findByUsuario(usuario)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SessaoEstudoResponseDTO toDTO(SessaoEstudo sessao) {
        SessaoEstudoResponseDTO dto = new SessaoEstudoResponseDTO();
        dto.setId(sessao.getId());
        dto.setUsuarioId(sessao.getUsuario().getId());
        dto.setStartedAt(sessao.getStartedAt());
        dto.setEndedAt(sessao.getEndedAt());
        dto.setStatus(sessao.getStatus());
        return dto;
    }
}
