package io.github.mathiasdziurkowski.socialmedia.teste.repositories;


import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Usuario findUsuarioByNome(String nome);
    Usuario findUsuarioByEmailAndSenha(String email, String senha);
    Usuario findUsuarioByNomeAndSenha(String nome, String senha);
}
