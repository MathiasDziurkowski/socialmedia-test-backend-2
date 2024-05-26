package io.github.mathiasdziurkowski.socialmedia.teste.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import io.github.mathiasdziurkowski.socialmedia.teste.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.security.chave}")
    private String token;
    @Value("${jwt.security.expiracao}")
    private String expiration;

    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(token);
        Long expiracao = Long.parseLong(expiration);
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(expiracao);
        Date data =  Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().setSubject(usuario.getEmail()).setExpiration(data).signWith( SignatureAlgorithm.HS512, token).compact();
    }

    private Claims obterClaims(String token ) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(token)
                .parseClaimsJws(token)
                .getBody();
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
    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

}
