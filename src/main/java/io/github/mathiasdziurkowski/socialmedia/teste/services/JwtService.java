package io.github.mathiasdziurkowski.socialmedia.teste.services;

import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.security.expiracao}")
    private String expiracao;
    @Value("${jwt.security.chave}")
    private String chave;

    public String gerarToken(Usuario usuario) {
        long expiration = Long.parseLong(expiracao);
        LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(expiration);
        Instant instant = Date.from(dataExpiracao.atZone(ZoneId.systemDefault()).toInstant()).toInstant();
        Date data = Date.from(instant);
        return Jwts.builder()
                .setSubject(usuario.getSenha())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chave)
                .compact();

    }

    public boolean tokenValido( String token ){
        try{
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data =
                    dataExpiracao.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    private Claims obterClaims(String token) {
        return Jwts.parser().setSigningKey(chave).parseClaimsJws(token).getBody();
    }
    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }

}
