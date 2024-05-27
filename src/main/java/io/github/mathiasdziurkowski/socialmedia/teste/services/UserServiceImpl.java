package io.github.mathiasdziurkowski.socialmedia.teste.services;

import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import io.github.mathiasdziurkowski.socialmedia.teste.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) usuarioRepository.findByEmail(username).stream().map(Usuario::getEmail);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow(() -> new UsernameNotFoundException(usuario.getEmail()));
        boolean senhasMatches = passwordEncoder.matches(user.getPassword(), usuario.getPassword());
        if (senhasMatches) {
            return user;
        }
        throw new RuntimeException("Senhas não batem");
    }

}