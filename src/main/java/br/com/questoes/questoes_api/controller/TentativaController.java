package br.com.questoes.questoes_api.controller;

import br.com.questoes.questoes_api.dto.tentativa.IniciarTentativaRequest;
import br.com.questoes.questoes_api.dto.tentativa.ResponderTentativaRequest;
import br.com.questoes.questoes_api.dto.tentativa.TentativaResponse;
import br.com.questoes.questoes_api.service.tentativa.TentativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tentativas")
public class TentativaController {
    @Autowired
    private TentativaService tentativaService;

    @PostMapping("/iniciar")
    public ResponseEntity<TentativaResponse> iniciar(@Validated @RequestBody IniciarTentativaRequest request) {
        TentativaResponse response = tentativaService.iniciarTentativa(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/responder")
    public ResponseEntity<TentativaResponse> responder(@PathVariable Long id, @Validated @RequestBody ResponderTentativaRequest request) {
        TentativaResponse response = tentativaService.responderTentativa(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<TentativaResponse> finalizar(@PathVariable Long id) {
        TentativaResponse response = tentativaService.finalizarTentativa(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TentativaResponse> buscar(@PathVariable Long id) {
        TentativaResponse response = tentativaService.buscarTentativa(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TentativaResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<TentativaResponse> responses = tentativaService.listarTentativasPorUsuario(usuarioId);
        return ResponseEntity.ok(responses);
    }
}
