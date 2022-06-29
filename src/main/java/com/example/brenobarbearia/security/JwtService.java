package com.example.brenobarbearia.security;

import com.example.brenobarbearia.model.entidade.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.ExpiredJwtException;

import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class JwtService {

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;
    @Value("${security.jwt.expiracao}")
    private String expiracao;

    public String gerarToken( Usuario usuario ){
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith( SignatureAlgorithm.HS512, chaveAssinatura )
                .compact();
    }

    private Claims obterClaims( String token ) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }
    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }
    public boolean tokenValido(String token){
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

}
