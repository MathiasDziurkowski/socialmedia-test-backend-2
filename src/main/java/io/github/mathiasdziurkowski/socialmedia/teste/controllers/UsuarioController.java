package io.github.mathiasdziurkowski.socialmedia.teste.controllers;

import io.github.mathiasdziurkowski.socialmedia.teste.dtos.CredenciaisDTO;
import io.github.mathiasdziurkowski.socialmedia.teste.dtos.TokenDTO;
import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import io.github.mathiasdziurkowski.socialmedia.teste.repositories.UsuarioRepository;
import io.github.mathiasdziurkowski.socialmedia.teste.services.JwtService;
import io.github.mathiasdziurkowski.socialmedia.teste.services.UsuarioService;
import io.github.mathiasdziurkowski.socialmedia.teste.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService = new UsuarioServiceImpl();
    @Autowired
    private JwtService jwtService;

    @PostMapping("cadastro")
    @ResponseBody
    public Usuario cadastrar(@RequestBody Usuario usuario) {
       return usuarioService.cadastrarUsuario(usuario);
    }
    @PostMapping("/auth")
    @ResponseBody
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        Usuario usuario = Usuario.builder().email(credenciaisDTO.getEmail()).senha(credenciaisDTO.getSenha()).build();
        UserDetails usuarioDetails = usuarioService.autenticar(usuario);
        String token = jwtService.generateToken(usuario);
        return new TokenDTO(usuario.getEmail(), token);
    }

}
