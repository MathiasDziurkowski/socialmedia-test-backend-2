package io.github.mathiasdziurkowski.socialmedia.teste.services;


import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService extends UserDetailsService {
    Usuario cadastrarUsuario(Usuario usuario);
    UserDetails autenticar(Usuario usuario);
}
