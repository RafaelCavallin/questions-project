package br.com.questoes.questoes_api.service.assunto;

import br.com.questoes.questoes_api.domain.Assunto;
import br.com.questoes.questoes_api.domain.Disciplina;
import br.com.questoes.questoes_api.dto.assunto.AssuntoRequestDTO;
import br.com.questoes.questoes_api.dto.assunto.AssuntoResponseDTO;
import br.com.questoes.questoes_api.exception.AssuntoNotFoundException;
import br.com.questoes.questoes_api.exception.DisciplinaNotFoundException;
import br.com.questoes.questoes_api.repository.AssuntoRepository;
import br.com.questoes.questoes_api.repository.disciplina.DisciplinaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class AssuntoService {
    private final AssuntoRepository assuntoRepository;
    private final DisciplinaRepository disciplinaRepository;

    @Transactional
    public AssuntoResponseDTO criar(AssuntoRequestDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new DisciplinaNotFoundException(dto.disciplinaId()));
        Assunto assunto = Assunto.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .disciplina(disciplina)
                .ativo(true)
                .build();
        assunto = assuntoRepository.save(assunto);
        return toResponseDTO(assunto);
    }

    public Page<AssuntoResponseDTO> listarTodos(Pageable pageable) {
        return assuntoRepository.findByAtivoTrueWithDisciplina(pageable)
                .map(this::toResponseDTO);
    }

    public AssuntoResponseDTO buscarPorId(Long id) {
        Assunto assunto = assuntoRepository.findByIdWithDisciplina(id)
                .orElseThrow(() -> new AssuntoNotFoundException(id));
        return toResponseDTO(assunto);
    }

    @Transactional
    public AssuntoResponseDTO atualizar(Long id, AssuntoRequestDTO dto) {
        Assunto assunto = assuntoRepository.findById(id)
                .filter(Assunto::getAtivo)
                .orElseThrow(() -> new AssuntoNotFoundException(id));

        if (dto.nome() != null) {
            assunto.setNome(dto.nome());
        }
        if (dto.descricao() != null) {
            assunto.setDescricao(dto.descricao());
        }
        if (dto.disciplinaId() != null) {
            Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                    .orElseThrow(() -> new DisciplinaNotFoundException(dto.disciplinaId()));
            assunto.setDisciplina(disciplina);
        }
        assunto = assuntoRepository.save(assunto);
        return toResponseDTO(assunto);
    }

    @Transactional
    public void deletar(Long id) {
        Assunto assunto = assuntoRepository.findById(id)
                .filter(Assunto::getAtivo)
                .orElseThrow(() -> new AssuntoNotFoundException(id));
        assunto.setAtivo(false);
        assuntoRepository.save(assunto);
    }

    private AssuntoResponseDTO toResponseDTO(Assunto assunto) {
        return new AssuntoResponseDTO(
                assunto.getId(),
                assunto.getNome(),
                assunto.getDescricao(),
                assunto.getDisciplina().getId(),
                assunto.getDisciplina().getNome(),
                assunto.getAtivo(),
                assunto.getCreatedAt(),
                assunto.getUpdatedAt()
        );
    }
}
