package br.com.questoes.questoes_api.service.usuario;

import br.com.questoes.questoes_api.domain.Role;
import br.com.questoes.questoes_api.domain.Usuario;
import br.com.questoes.questoes_api.dto.usuario.UsuarioCreateRequest;
import br.com.questoes.questoes_api.repository.UsuarioRepository;
import br.com.questoes.questoes_api.repository.role.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void deletarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    public Usuario editarUsuario(Long id, UsuarioCreateRequest dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getAtivo() != null) {
            usuario.setAtivo(dto.getAtivo());
        }
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        if (dto.getRolesIds() != null) {
            Set<Role> roles = roleRepository.findAllById(dto.getRolesIds()).stream().collect(Collectors.toSet());
            usuario.setRoles(roles);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Page<Usuario> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.findAll(pageable);
    }
    
    public Usuario criarUsuario(UsuarioCreateRequest dto) {
        Set<Role> roles = roleRepository.findAllById(dto.getRolesIds()).stream().collect(Collectors.toSet());
        Usuario usuario = Usuario.builder()
            .nome(dto.getNome())
            .email(dto.getEmail())
            .senha(passwordEncoder.encode(dto.getSenha()))
            .ativo(dto.getAtivo())
            .roles(roles)
            .build();
        return usuarioRepository.save(usuario);
    }
}