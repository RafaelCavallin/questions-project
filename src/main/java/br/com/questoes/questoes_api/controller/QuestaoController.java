package br.com.questoes.questoes_api.controller;

import br.com.questoes.questoes_api.dto.questao.QuestaoRequestDTO;
import br.com.questoes.questoes_api.dto.questao.QuestaoResponseDTO;
import br.com.questoes.questoes_api.enums.NivelDificuldadeEnum;
import br.com.questoes.questoes_api.service.QuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/questoes")
public class QuestaoController {
    @Autowired private QuestaoService questaoService;

    @PostMapping
    public ResponseEntity<QuestaoResponseDTO> criar(@Valid @RequestBody QuestaoRequestDTO dto) {
        return ResponseEntity.status(201).body(questaoService.criarQuestao(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestaoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody QuestaoRequestDTO dto) {
        return ResponseEntity.ok(questaoService.atualizarQuestao(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(questaoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<QuestaoResponseDTO>> listar(
            @RequestParam(required = false) Long disciplinaId,
            @RequestParam(required = false) Long bancaId,
            @RequestParam(required = false) NivelDificuldadeEnum nivel,
            Pageable pageable) {
        return ResponseEntity.ok(questaoService.listarPaginado(disciplinaId, bancaId, nivel, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        questaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
