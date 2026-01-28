package br.com.questoes.questoes_api.controller.usuario;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.questoes.questoes_api.domain.Usuario;
import br.com.questoes.questoes_api.dto.usuario.UsuarioCreateRequest;
import br.com.questoes.questoes_api.dto.usuario.UsuarioResponse;
import br.com.questoes.questoes_api.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> editar(@PathVariable Long id, @RequestBody UsuarioCreateRequest dto) {
        Usuario usuario = usuarioService.editarUsuario(id, dto);
        UsuarioResponse response = new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getAtivo(),
            usuario.getRoles().stream().map(r -> r.getNome()).collect(java.util.stream.Collectors.toSet())
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioResponse response = new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getAtivo(),
            usuario.getRoles().stream().map(r -> r.getNome()).collect(java.util.stream.Collectors.toSet())
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Usuario> usuarios = usuarioService.listarTodos(page, size);
        Page<UsuarioResponse> response = usuarios.map(usuario -> new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getAtivo(),
            usuario.getRoles().stream().map(r -> r.getNome()).collect(java.util.stream.Collectors.toSet())
        ));
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioCreateRequest dto) {
        Usuario usuario = usuarioService.criarUsuario(dto);
        UsuarioResponse response = new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getAtivo(),
            usuario.getRoles().stream().map(r -> r.getNome()).collect(Collectors.toSet())
        );
        return ResponseEntity.ok(response);
    }

}
