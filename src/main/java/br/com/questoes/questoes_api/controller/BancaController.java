package br.com.questoes.questoes_api.controller;

import br.com.questoes.questoes_api.dto.banca.BancaRequestDTO;
import br.com.questoes.questoes_api.dto.banca.BancaResponseDTO;
import br.com.questoes.questoes_api.service.BancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/bancas")
@Validated
public class BancaController {
    @Autowired
    private BancaService bancaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BancaResponseDTO> create(@Valid @RequestBody BancaRequestDTO dto) {
        BancaResponseDTO response = bancaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','ALUNO')")
    public ResponseEntity<Page<BancaResponseDTO>> findAll(Pageable pageable) {
        Page<BancaResponseDTO> page = bancaService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','ALUNO')")
    public ResponseEntity<BancaResponseDTO> findById(@PathVariable Long id) {
        BancaResponseDTO response = bancaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BancaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BancaRequestDTO dto) {
        BancaResponseDTO response = bancaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bancaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
