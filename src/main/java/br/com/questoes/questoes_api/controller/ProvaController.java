package br.com.questoes.questoes_api.controller;

import br.com.questoes.questoes_api.dto.ProvaCreateDTO;
import br.com.questoes.questoes_api.dto.ProvaDTO;
import br.com.questoes.questoes_api.dto.ProvaUpdateDTO;
import br.com.questoes.questoes_api.service.ProvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/provas")
public class ProvaController {
    @Autowired
    private ProvaService provaService;

    @PostMapping
    public ResponseEntity<ProvaDTO> criar(@RequestBody ProvaCreateDTO dto) {
        ProvaDTO prova = provaService.criarProva(dto);
        return ResponseEntity.ok(prova);
    }

    @GetMapping
    public ResponseEntity<List<ProvaDTO>> listar() {
        List<ProvaDTO> provas = provaService.listarProvas();
        return ResponseEntity.ok(provas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvaDTO> buscar(@PathVariable Long id) {
        return provaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvaDTO> atualizar(@PathVariable Long id, @RequestBody ProvaUpdateDTO dto) {
        return provaService.atualizarProva(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        boolean ok = provaService.ativarProva(id);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        boolean ok = provaService.desativarProva(id);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
