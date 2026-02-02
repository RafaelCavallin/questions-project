package br.com.questoes.questoes_api.service;

import br.com.questoes.questoes_api.dto.ProvaCreateDTO;
import br.com.questoes.questoes_api.dto.ProvaDTO;
import br.com.questoes.questoes_api.dto.ProvaUpdateDTO;
import br.com.questoes.questoes_api.model.Prova;
import br.com.questoes.questoes_api.domain.Disciplina;
import br.com.questoes.questoes_api.domain.Questao;
import br.com.questoes.questoes_api.repository.ProvaRepository;
import br.com.questoes.questoes_api.repository.BancaRepository;
import br.com.questoes.questoes_api.repository.QuestaoRepository;
import br.com.questoes.questoes_api.repository.disciplina.DisciplinaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvaService {

    @Autowired
    private ProvaRepository provaRepository;
    @Autowired
    private BancaRepository bancaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private QuestaoRepository questaoRepository;

    @Transactional
    public ProvaDTO criarProva(ProvaCreateDTO dto) {
        Prova prova = new Prova();
        prova.setTitulo(dto.getTitulo());
        prova.setDescricao(dto.getDescricao());
        prova.setObjetivo(dto.getObjetivo());
        prova.setTempoEmMinutos(dto.getTempoEmMinutos());
        prova.setAtiva(true);
        if (dto.getBancaId() != null) {
            bancaRepository.findById(dto.getBancaId()).ifPresent(prova::setBanca);
        }
        if (dto.getDisciplinasIds() != null && !dto.getDisciplinasIds().isEmpty()) {
            prova.setDisciplinas(new java.util.HashSet<>(disciplinaRepository.findAllById(dto.getDisciplinasIds())));
        }
        if (dto.getQuestoesIds() != null && !dto.getQuestoesIds().isEmpty()) {
            prova.setQuestoes(new java.util.HashSet<>(questaoRepository.findAllById(dto.getQuestoesIds())));
        }
        prova = provaRepository.save(prova);
        return toDTO(prova);
    }

    @Transactional
    public List<ProvaDTO> listarProvas() {
        return provaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public Optional<ProvaDTO> buscarPorId(Long id) {
        return provaRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public Optional<ProvaDTO> atualizarProva(Long id, ProvaUpdateDTO dto) {
        Optional<Prova> opt = provaRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Prova prova = opt.get();
        if (!prova.isEditavel()) return Optional.empty();
        if (dto.getTitulo() != null) {
            prova.setTitulo(dto.getTitulo());
        }
        if (dto.getBancaId() != null) {
            bancaRepository.findById(dto.getBancaId()).ifPresent(prova::setBanca);
        }
        if (dto.getDescricao() != null) {
            prova.setDescricao(dto.getDescricao());
        }
        if (dto.getTempoEmMinutos() != null) {
            prova.setTempoEmMinutos(dto.getTempoEmMinutos());
        }
        if (dto.getAtiva() != null) {
            prova.setAtiva(dto.getAtiva());
        }
        if (dto.getDisciplinasIds() != null) {
            prova.setDisciplinas(new java.util.HashSet<>(disciplinaRepository.findAllById(dto.getDisciplinasIds())));
        }
        prova = provaRepository.save(prova);
        return Optional.of(toDTO(prova));
    }

    @Transactional
    public boolean ativarProva(Long id) {
        Optional<Prova> opt = provaRepository.findById(id);
        if (opt.isEmpty()) return false;
        Prova prova = opt.get();
        prova.setAtiva(true);
        provaRepository.save(prova);
        return true;
    }

    @Transactional
    public boolean desativarProva(Long id) {
        Optional<Prova> opt = provaRepository.findById(id);
        if (opt.isEmpty()) return false;
        Prova prova = opt.get();
        prova.setAtiva(false);
        provaRepository.save(prova);
        return true;
    }

    private ProvaDTO toDTO(Prova prova) {
        ProvaDTO dto = new ProvaDTO();
        dto.setId(prova.getId());
        dto.setTitulo(prova.getTitulo());
        dto.setDescricao(prova.getDescricao());
        dto.setObjetivo(prova.getObjetivo());
        dto.setTempoEmMinutos(prova.getTempoEmMinutos());
        dto.setAtiva(prova.getAtiva());
        dto.setCriadaEm(prova.getCriadaEm());
        dto.setAtualizadaEm(prova.getAtualizadaEm());
        if (prova.getBanca() != null) {
            dto.setBancaId(prova.getBanca().getId());
        }
        if (prova.getDisciplinas() != null) {
            java.util.Set<Long> ids = new java.util.HashSet<>();
            for (Disciplina d : prova.getDisciplinas()) {
                ids.add(d.getId());
            }
            dto.setDisciplinasIds(ids);
        }
        if (prova.getQuestoes() != null) {
            java.util.Set<Long> ids = new java.util.HashSet<>();
            for (Questao q : prova.getQuestoes()) {
                ids.add(q.getId());
            }
            dto.setQuestoesIds(ids);
        }
        return dto;
    }
}
