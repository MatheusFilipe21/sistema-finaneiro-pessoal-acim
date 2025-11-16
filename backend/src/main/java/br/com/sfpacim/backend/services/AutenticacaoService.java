package br.com.sfpacim.backend.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.sfpacim.backend.dtos.autenticacao.DadosAutenticacaoDTO;
import br.com.sfpacim.backend.dtos.autenticacao.DadosTokenJWTDTO;
import br.com.sfpacim.backend.models.Usuario;

/**
 * Serviço responsável por orquestrar a lógica de autenticação.
 *
 * @author Matheus F. N. Pereira
 */
@Service
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Construtor para Injeção de Dependências.
     *
     * @param authenticationManager O gerenciador de autenticação do Spring.
     * @param tokenService          O serviço para geração de tokens JWT.
     */
    public AutenticacaoService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Orquestra a tentativa de login (RF10, RF11, RF12).
     *
     * @param dados Os dados de autenticação (email, senha).
     * @return O DTO contendo o Token JWT.
     */
    public DadosTokenJWTDTO login(DadosAutenticacaoDTO dados) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(),
                dados.senha());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String tokenJWT = tokenService.gerarToken(usuario);

        return new DadosTokenJWTDTO(tokenJWT);
    }
}
