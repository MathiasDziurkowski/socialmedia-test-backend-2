package io.github.mathiasdziurkowski.socialmedia.teste.services;

import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import io.github.mathiasdziurkowski.socialmedia.teste.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return usuarioRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("usuario nao encontrado"));
    }

    public Usuario cadastrar(Usuario usuario){
        Usuario usuarioInvalido = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow(() -> null);
        if (usuarioInvalido != null){
            throw new RuntimeException("Usuário já existente");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return usuario;
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow(() -> new UsernameNotFoundException(usuario.getEmail()));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        boolean senhasMatches = passwordEncoder.matches(user.getPassword(), usuario.getSenha());
        if (senhasMatches) {
            return user;
        }

        throw new RuntimeException("Senhas não batem ");
    }

}
