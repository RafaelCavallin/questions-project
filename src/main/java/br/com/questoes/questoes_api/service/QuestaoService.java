package br.com.questoes.questoes_api.service;

import br.com.questoes.questoes_api.domain.*;
import br.com.questoes.questoes_api.dto.questao.QuestaoRequestDTO;
import br.com.questoes.questoes_api.dto.questao.QuestaoResponseDTO;
import br.com.questoes.questoes_api.enums.NivelDificuldadeEnum;
import br.com.questoes.questoes_api.exception.NotFoundException;
import br.com.questoes.questoes_api.mapper.QuestaoMapper;
import br.com.questoes.questoes_api.repository.*;
import br.com.questoes.questoes_api.repository.disciplina.DisciplinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestaoService {
    
    @Autowired 
    private QuestaoRepository questaoRepository;
    
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    @Autowired
    private BancaRepository bancaRepository;
    
    @Autowired
    private QuestaoMapper questaoMapper;

    @Transactional
    public QuestaoResponseDTO criarQuestao(QuestaoRequestDTO dto) {
        Questao questao = questaoMapper.toEntity(dto);
        questao.setDisciplina(disciplinaRepository.findById(dto.getDisciplinaId())
            .orElseThrow(() -> new NotFoundException("Disciplina não encontrada")));
        questao.setAssunto(assuntoRepository.findById(dto.getAssuntoId())
            .orElseThrow(() -> new NotFoundException("Assunto não encontrado")));
        questao.setBanca(bancaRepository.findById(dto.getBancaId())
            .orElseThrow(() -> new NotFoundException("Banca não encontrada")));
        questao.setAtivo(true);
        return questaoMapper.toDTO(questaoRepository.save(questao));
    }

    @Transactional
    public QuestaoResponseDTO atualizarQuestao(Long id, QuestaoRequestDTO dto) {
        Questao questao = questaoRepository.findById(id)
            .filter(Questao::getAtivo)
            .orElseThrow(() -> new NotFoundException("Questão não encontrada"));

        if (dto.getEnunciado() != null) {
            questao.setEnunciado(dto.getEnunciado());
        }
        if (dto.getNivelDificuldade() != null) {
            questao.setNivelDificuldade(dto.getNivelDificuldade());
        }
        if (dto.getDisciplinaId() != null) {
            questao.setDisciplina(disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada")));
        }
        if (dto.getAssuntoId() != null) {
            questao.setAssunto(assuntoRepository.findById(dto.getAssuntoId())
                .orElseThrow(() -> new NotFoundException("Assunto não encontrado")));
        }
        if (dto.getBancaId() != null) {
            questao.setBanca(bancaRepository.findById(dto.getBancaId())
                .orElseThrow(() -> new NotFoundException("Banca não encontrada")));
        }

        if (questao instanceof QuestaoMultiplaEscolha) {
            QuestaoMultiplaEscolha qm = (QuestaoMultiplaEscolha) questao;
            if (dto.getAlternativaA() != null) qm.setAlternativaA(dto.getAlternativaA());
            if (dto.getAlternativaB() != null) qm.setAlternativaB(dto.getAlternativaB());
            if (dto.getAlternativaC() != null) qm.setAlternativaC(dto.getAlternativaC());
            if (dto.getAlternativaD() != null) qm.setAlternativaD(dto.getAlternativaD());
            if (dto.getAlternativaE() != null) qm.setAlternativaE(dto.getAlternativaE());
            if (dto.getAlternativaCorreta() != null) qm.setAlternativaCorreta(dto.getAlternativaCorreta());
        } else if (questao instanceof QuestaoCertoErrado) {
            QuestaoCertoErrado qc = (QuestaoCertoErrado) questao;
            if (dto.getAlternativaCorreta() != null) {
                qc.setAlternativaCorreta(dto.getAlternativaCorreta());
            }
        }

        return questaoMapper.toDTO(questaoRepository.save(questao));
    }

    @Transactional(readOnly = true)
    public QuestaoResponseDTO buscarPorId(Long id) {
        Questao questao = questaoRepository.findById(id)
            .filter(Questao::getAtivo)
            .orElseThrow(() -> new NotFoundException("Questão não encontrada"));
        return questaoMapper.toDTO(questao);
    }

    @Transactional(readOnly = true)
    public Page<QuestaoResponseDTO> listarPaginado(Long disciplinaId, Long bancaId, NivelDificuldadeEnum nivel, Pageable pageable) {
        return questaoRepository.findByFilters(disciplinaId, bancaId, nivel, pageable)
            .map(questaoMapper::toDTO);
    }

    @Transactional
    public void deletar(Long id) {
        Questao questao = questaoRepository.findById(id)
            .filter(Questao::getAtivo)
            .orElseThrow(() -> new NotFoundException("Questão não encontrada"));
        questao.setAtivo(false);
        questaoRepository.save(questao);
    }
}
