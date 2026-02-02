package br.com.questoes.questoes_api.controller;

import br.com.questoes.questoes_api.dto.sessaoestudo.SessaoEstudoResponseDTO;
import br.com.questoes.questoes_api.service.SessaoEstudoService;
import br.com.questoes.questoes_api.domain.Usuario;
import br.com.questoes.questoes_api.domain.UsuarioDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sessoes-estudo")
public class SessaoEstudoController {
    @Autowired
    private SessaoEstudoService sessaoEstudoService;

    @PostMapping
    public ResponseEntity<SessaoEstudoResponseDTO> criar(@AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Usuario usuario = usuarioDetails.getUsuario();
        return ResponseEntity.status(201).body(sessaoEstudoService.criarSessao(usuario));
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<SessaoEstudoResponseDTO> finalizar(@PathVariable Long id, @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Usuario usuario = usuarioDetails.getUsuario();
        return ResponseEntity.ok(sessaoEstudoService.finalizarSessao(id, usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoEstudoResponseDTO> findById(@PathVariable Long id, @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Usuario usuario = usuarioDetails.getUsuario();
        return ResponseEntity.ok(sessaoEstudoService.findById(id, usuario));
    }

    @GetMapping
    public ResponseEntity<List<SessaoEstudoResponseDTO>> list(@AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Usuario usuario = usuarioDetails.getUsuario();
        return ResponseEntity.ok(sessaoEstudoService.listByUser(usuario));
    }
}
