package br.com.questoes.questoes_api.controller.role;

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

import br.com.questoes.questoes_api.domain.Role;
import br.com.questoes.questoes_api.dto.role.RoleCreateRequestDto;
import br.com.questoes.questoes_api.service.role.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    
    @GetMapping("/{id}")
    public ResponseEntity<Role> buscarPorId(@PathVariable Long id) {
        Role role = roleService.buscarPorId(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<Role>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        org.springframework.data.domain.Page<Role> roles = roleService.listarTodos(page, size);
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> editar(@PathVariable Long id, @RequestBody RoleCreateRequestDto dto) {
        Role role = roleService.editarRole(id, dto.nome(), dto.ativo());
        return ResponseEntity.ok(role);
    }

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> criar(@RequestBody RoleCreateRequestDto dto) {
        Role role = roleService.criarRole(dto.nome());
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        roleService.deletarRole(id);
        return ResponseEntity.noContent().build();
    }
}
