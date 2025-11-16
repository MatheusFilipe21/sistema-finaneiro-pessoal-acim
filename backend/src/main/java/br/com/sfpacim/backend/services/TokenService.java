package br.com.sfpacim.backend.services;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sfpacim.backend.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Serviço responsável por gerenciar Tokens JWT (Geração e Validação).
 *
 * @author Matheus F. N. Pereira
 */
@Service
public class TokenService {

    /**
     * A chave secreta usada para assinar o token.
     */
    @Value("${api.security.token.secret}")
    private String chaveSecreta;

    /**
     * O tempo de expiração do token.
     */
    @Value("${api.security.token.expiration-ms}")
    private Long tempoExpiracaoMs;

    /**
     * Gera um novo Token JWT assinado para um usuário autenticado.
     *
     * @param usuario A entidade {@link Usuario} autenticada.
     * @return Uma string (o Token JWT).
     */
    public String gerarToken(Usuario usuario) {
        SecretKey chave = this.getChaveDeAssinatura();
        Instant agora = LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"));
        Instant expiracao = agora.plusMillis(this.tempoExpiracaoMs);

        return Jwts.builder()
                .issuer("SFP-ACIM API")
                .subject(usuario.getEmail())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expiracao))
                .signWith(chave)
                .compact();
    }

    /**
     * Valida um Token JWT (usado pelo Filtro JWT).
     *
     * @param token O token (String) vindo do cabeçalho Authorization.
     * @return O "subject" (e-mail) do usuário se o token for válido.
     * @throws io.jsonwebtoken.JwtException Se o token for inválido, expirado
     *                                      ou a assinatura falhar.
     */
    public String validarToken(String token) {
        SecretKey chave = this.getChaveDeAssinatura();

        return Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Helper (auxiliar) que converte a chave secreta (String)
     * do .yml para o formato SecretKey (HMAC-SHA) exigido pela
     * biblioteca jjwt.
     */
    private SecretKey getChaveDeAssinatura() {
        return Keys.hmacShaKeyFor(this.chaveSecreta.getBytes(StandardCharsets.UTF_8));
    }
}
