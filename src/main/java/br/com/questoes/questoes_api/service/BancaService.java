package br.com.questoes.questoes_api.service;

import br.com.questoes.questoes_api.domain.Banca;
import br.com.questoes.questoes_api.dto.banca.BancaRequestDTO;
import br.com.questoes.questoes_api.dto.banca.BancaResponseDTO;
import br.com.questoes.questoes_api.exception.BancaNotFoundException;
import br.com.questoes.questoes_api.repository.BancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BancaService {
    @Autowired
    private BancaRepository bancaRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public BancaResponseDTO create(BancaRequestDTO dto) {
        Banca banca = new Banca();
        banca.setNome(dto.getNome());
        banca.setSigla(dto.getSigla());
        banca.setAtivo(true);
        Banca saved = bancaRepository.save(banca);
        return toResponseDTO(saved);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','ALUNO')")
    @Transactional(readOnly = true)
    public Page<BancaResponseDTO> findAll(Pageable pageable) {
        return bancaRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','ALUNO')")
    @Transactional(readOnly = true)
    public BancaResponseDTO findById(Long id) {
        Banca banca = bancaRepository.findById(id)
                .filter(Banca::getAtivo)
                .orElseThrow(() -> new BancaNotFoundException(id));
        return toResponseDTO(banca);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public BancaResponseDTO update(Long id, BancaRequestDTO dto) {
        Banca banca = bancaRepository.findById(id)
                .filter(Banca::getAtivo)
                .orElseThrow(() -> new BancaNotFoundException(id));
        if (dto.getNome() != null) {
            banca.setNome(dto.getNome());
        }
        if (dto.getSigla() != null) {
            banca.setSigla(dto.getSigla());
        }
        Banca updated = bancaRepository.save(banca);
        return toResponseDTO(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Long id) {
        Banca banca = bancaRepository.findById(id)
                .filter(Banca::getAtivo)
                .orElseThrow(() -> new BancaNotFoundException(id));
        banca.setAtivo(false);
        bancaRepository.save(banca);
    }

    private BancaResponseDTO toResponseDTO(Banca banca) {
        BancaResponseDTO dto = new BancaResponseDTO();
        dto.setId(banca.getId());
        dto.setNome(banca.getNome());
        dto.setSigla(banca.getSigla());
        dto.setAtivo(banca.getAtivo());
        dto.setCreatedAt(banca.getCreatedAt());
        dto.setUpdatedAt(banca.getUpdatedAt());
        return dto;
    }
}
