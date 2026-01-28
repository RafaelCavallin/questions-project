package br.com.questoes.questoes_api.service.disciplina;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.questoes.questoes_api.domain.Disciplina;
import br.com.questoes.questoes_api.dto.disciplina.DisciplinaRequestDTO;
import br.com.questoes.questoes_api.dto.disciplina.DisciplinaResponseDTO;
import br.com.questoes.questoes_api.repository.disciplina.DisciplinaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository repository;

    @Transactional
    public DisciplinaResponseDTO criar(DisciplinaRequestDTO dto) {

        repository.findByNomeIgnoreCase(dto.nome())
                .ifPresent(d -> {
                    throw new IllegalArgumentException("Disciplina já existe");
                });

        Disciplina disciplina = Disciplina.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .ativa(true)
                .build();

        Disciplina salva = repository.save(disciplina);

        return toResponseDTO(salva);
    }

    @Transactional
    public void deletar(Long id) {
        Disciplina disciplina = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        repository.delete(disciplina);
    }

    @Transactional(readOnly = true)
    public DisciplinaResponseDTO buscarPorId(Long id) {
        Disciplina disciplina = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        return toResponseDTO(disciplina);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<DisciplinaResponseDTO> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    @Transactional
    public DisciplinaResponseDTO atualizar(Long id, DisciplinaRequestDTO dto) {
        Disciplina disciplina = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        disciplina.setNome(dto.nome());
        disciplina.setDescricao(dto.descricao());

        return toResponseDTO(disciplina);
    }

    @Transactional
    public void desativar(Long id) {
        Disciplina disciplina = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        disciplina.setAtiva(false);
    }

    private DisciplinaResponseDTO toResponseDTO(Disciplina d) {
        return new DisciplinaResponseDTO(
                d.getId(),
                d.getNome(),
                d.getDescricao(),
                d.getAtiva()
        );
    }
}
