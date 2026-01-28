package br.com.questoes.questoes_api.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.questoes.questoes_api.domain.Role;
import br.com.questoes.questoes_api.repository.role.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role buscarPorId(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));
    }

    public org.springframework.data.domain.Page<Role> listarTodos(int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return roleRepository.findAll(pageable);
    }

    public Role editarRole(Long id, String nome, Boolean ativo) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role não encontrada"));
        role.setNome(nome);
        role.setAtivo(ativo);
        return roleRepository.save(role);
    }

    public Role criarRole(String nome) {
        Role role = new Role(nome);
        return roleRepository.save(role);
    }

    public void deletarRole(Long id) {
        roleRepository.deleteById(id);
    }
    
}
