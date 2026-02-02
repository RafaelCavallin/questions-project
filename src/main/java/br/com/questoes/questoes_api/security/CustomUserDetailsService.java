package br.com.questoes.questoes_api.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import br.com.questoes.questoes_api.domain.Usuario;
import br.com.questoes.questoes_api.domain.UsuarioDetails;
import br.com.questoes.questoes_api.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));

        return new UsuarioDetails(usuario);
    }
}

