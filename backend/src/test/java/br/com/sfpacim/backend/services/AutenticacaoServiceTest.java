package br.com.sfpacim.backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.com.sfpacim.backend.dtos.autenticacao.DadosAutenticacaoDTO;
import br.com.sfpacim.backend.dtos.autenticacao.DadosTokenJWTDTO;
import br.com.sfpacim.backend.models.Usuario;

/**
 * Testes unitários para a classe {@link AutenticacaoService}.
 * 
 * <p>
 * Utiliza Mockito para isolar o serviço das dependências externas
 * (AuthenticationManager e TokenService).
 *
 * @author Matheus F. N. Pereira
 */
@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    private static final String EMAIL = "matheusfnpereira@gmail.com";
    private static final String SENHA = "Ab123456";
    private static final String TOKEN_JWT = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJTRlAtQUNJTSBBUEkiLCJzdWIiOiJtYXRoZXVzZm5wZXJlaXJhQGdtYWlsLmNvbSIsImlhdCI6MTc2MzMwNjE3NiwiZXhwIjoxNzYzMzM0OTc2fQ.e90EOyfiPFUE4Mu5LgbZEtrYnQIGzueecgm4G-fWIKTtSr7IuxC1X_hBkltJBRxHo9ocTvQFje44r0g84TqaiQ";

    /**
     * Testa o método {@link AutenticacaoService#login(DadosAutenticacaoDTO)}.
     * Valida o cenário de sucesso.
     * 
     * <p>
     * Verifica se o serviço chama o AuthenticationManager e o TokenService
     * corretamente e retorna o DTO do token.
     */
    @Test
    @DisplayName("login: Quando credenciais válidas, deve autenticar e retornar o token JWT")
    void testeLogin_QuandoCredenciaisValidas_DeveRetornarToken() {
        DadosAutenticacaoDTO dadosLogin = new DadosAutenticacaoDTO(EMAIL, SENHA);

        Usuario usuarioMock = new Usuario(UUID.randomUUID(), "Matheus Filipe do Nascimento Pereira", EMAIL,
                "$2a$10$VUI0N7kPFDVnD6XZbLni6uyg3UF0RU/fQRNHnZb6oWhTGT3R9YqgK");

        Authentication authenticationMock = new UsernamePasswordAuthenticationToken(usuarioMock, null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authenticationMock);

        when(tokenService.gerarToken(usuarioMock)).thenReturn(TOKEN_JWT);

        DadosTokenJWTDTO resultadoDTO = autenticacaoService.login(dadosLogin);

        assertNotNull(resultadoDTO, "O DTO de token não deve ser nulo");
        assertEquals(TOKEN_JWT, resultadoDTO.token(), "O token JWT retornado deve ser o esperado");

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).gerarToken(usuarioMock);
    }

    /**
     * Testa o método {@link AutenticacaoService#login(DadosAutenticacaoDTO)}.
     * Valida o cenário de falha (credenciais inválidas).
     * 
     * <p>
     * Verifica se o serviço repassa a exceção (AuthenticationException)
     * lançada pelo AuthenticationManager.
     */
    @Test
    @DisplayName("login: Quando credenciais inválidas, deve lançar AuthenticationException")
    void testeLogin_QuandoCredenciaisInvalidas_DeveLancarExcecao() {
        DadosAutenticacaoDTO dadosLogin = new DadosAutenticacaoDTO(EMAIL, "senhaErrada");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.core.AuthenticationException("Credenciais inválidas") {
                });

        assertThrows(AuthenticationException.class, () -> {
            autenticacaoService.login(dadosLogin);
        }, "Deveria lançar AuthenticationException");
    }
}
