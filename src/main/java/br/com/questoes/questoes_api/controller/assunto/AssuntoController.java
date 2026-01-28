package br.com.questoes.questoes_api.controller.assunto;

import br.com.questoes.questoes_api.dto.assunto.AssuntoRequestDTO;
import br.com.questoes.questoes_api.dto.assunto.AssuntoResponseDTO;
import br.com.questoes.questoes_api.service.assunto.AssuntoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/assuntos")
@RequiredArgsConstructor
public class AssuntoController {
    private final AssuntoService assuntoService;

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @PostMapping
    public ResponseEntity<AssuntoResponseDTO> criar(@RequestBody @Valid AssuntoRequestDTO dto) {
        AssuntoResponseDTO response = assuntoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','ALUNO')")
    @GetMapping
    public ResponseEntity<Page<AssuntoResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(assuntoService.listarTodos(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','ALUNO')")
    @GetMapping("/{id}")
    public ResponseEntity<AssuntoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assuntoService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        assuntoService.deletar(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<AssuntoResponseDTO> atualizar(@PathVariable Long id, @RequestBody AssuntoRequestDTO dto) {
        return ResponseEntity.ok(assuntoService.atualizar(id, dto));
    }
}
