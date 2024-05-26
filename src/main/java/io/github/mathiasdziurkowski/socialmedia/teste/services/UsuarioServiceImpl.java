package io.github.mathiasdziurkowski.socialmedia.teste.services;

import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import io.github.mathiasdziurkowski.socialmedia.teste.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    @Lazy
    private PasswordEncoder  passwordEncoder;

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return usuario;
    }
    @Override
    public UserDetails autenticar(Usuario usuario) {
        UserDetails usuarioAutenticado = loadUserByUsername(usuario.getNome());
        passwordEncoder.matches(usuario.getSenha(), usuarioAutenticado.getPassword());
        return usuarioAutenticado;
    }

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
       Usuario usuario = usuarioRepository.findUsuarioByNome(nome);
        return User
                .builder()
                .username(usuario.getNome())
                .password(usuario.getSenha())
                .build();
    }
}
