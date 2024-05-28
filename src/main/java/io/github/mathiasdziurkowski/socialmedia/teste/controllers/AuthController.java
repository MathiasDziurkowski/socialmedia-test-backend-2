package io.github.mathiasdziurkowski.socialmedia.teste.controllers;

import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.mathiasdziurkowski.socialmedia.teste.dtos.LoginDTO;
import io.github.mathiasdziurkowski.socialmedia.teste.dtos.PerfilDTO;
import io.github.mathiasdziurkowski.socialmedia.teste.dtos.TokenDTO;
import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import io.github.mathiasdziurkowski.socialmedia.teste.repositories.UsuarioRepository;
import io.github.mathiasdziurkowski.socialmedia.teste.services.JwtService;
import io.github.mathiasdziurkowski.socialmedia.teste.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/cadastro")
    @ResponseBody
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        userService.cadastrar(usuario);
        return ResponseEntity.ok(usuario);
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .email(loginDTO.email())
                    .senha(loginDTO.senha()).build();
            userService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenDTO(usuario.getEmail(), token));

        }catch (JWTCreationException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/perfil")
    @ResponseBody
    public ResponseEntity<Usuario> perfil(@RequestBody PerfilDTO perfilDTO) {
        Usuario usuarioAchado = usuarioRepository.findByNome(perfilDTO.nome()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return ResponseEntity.ok(usuarioAchado);
    }


}
