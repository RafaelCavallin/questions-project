package br.com.questoes.questoes_api.controller.auth;

import br.com.questoes.questoes_api.domain.Usuario;
import br.com.questoes.questoes_api.dto.login.LoginRequest;
import br.com.questoes.questoes_api.repository.UsuarioRepository;
import br.com.questoes.questoes_api.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        java.util.List<String> roles = usuario.getRoles().stream()
            .map(role -> "ROLE_" + role.getNome().toUpperCase())
            .toList();

        return jwtService.generateToken(usuario.getEmail(), roles);
    }
}
