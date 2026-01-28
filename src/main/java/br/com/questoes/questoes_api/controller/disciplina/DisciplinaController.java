package br.com.questoes.questoes_api.controller.disciplina;

import br.com.questoes.questoes_api.dto.disciplina.DisciplinaRequestDTO;
import br.com.questoes.questoes_api.dto.disciplina.DisciplinaResponseDTO;
import br.com.questoes.questoes_api.service.disciplina.DisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinas")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisciplinaResponseDTO criar(@RequestBody @Valid DisciplinaRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> buscarPorId(@PathVariable Long id) {
        DisciplinaResponseDTO dto = service.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}/remover")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @GetMapping
    public ResponseEntity<Page<DisciplinaResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DisciplinaResponseDTO> disciplinas = service.listar(pageable);
        return ResponseEntity.ok(disciplinas);
    }

    @PutMapping("/{id}")
    public DisciplinaResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DisciplinaRequestDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long id) {
        service.desativar(id);
    }
}
