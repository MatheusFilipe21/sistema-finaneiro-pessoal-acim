package br.com.sfpacim.backend.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sfpacim.backend.dtos.usuario.DadosCadastroUsuarioDTO;
import br.com.sfpacim.backend.dtos.usuario.UsuarioDTO;
import br.com.sfpacim.backend.exceptions.ViolacaoDadosExcecao;
import br.com.sfpacim.backend.models.Usuario;
import br.com.sfpacim.backend.repositories.UsuarioRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada ao {@link Usuario}.
 *
 * @author Matheus F. N. Pereira
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor para Injeção de Dependências.
     * 
     * <p>
     * O Spring injeta automaticamente as instâncias de UsuarioRepository
     * e PasswordEncoder quando esta classe é criada.
     *
     * @param usuarioRepository O repositório para acesso aos dados do usuário.
     * @param passwordEncoder   O bean para codificação de senhas (BCrypt).
     */
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Processa o registro de um novo usuário no sistema.
     *
     * <p>
     * Este método orquestra a conversão do DTO (RF06 - Hash BCrypt)
     * e a persistência (RF04 - Verificação de duplicidade),
     * retornando o usuário criado como um DTO de resposta.
     *
     * @param dados Os dados de cadastro (DTO) já validados pelo controller.
     * @return O {@link UsuarioDTO} contendo os dados públicos do usuário
     *         recém-criado.
     * @throws ViolacaoDadosExcecao Se o e-mail já existir no banco (RF04).
     */
    public UsuarioDTO registrar(DadosCadastroUsuarioDTO dados) throws ViolacaoDadosExcecao {
        return paraDTO(this.salvarEntidade(paraEntidade(dados)));
    }

    /**
     * Converte a entidade {@link Usuario} (persistida) para um
     * {@link UsuarioDTO} (objeto de resposta).
     * <p>
     * Este método garante que dados sensíveis (como a senha)
     * não sejam expostos na API.
     *
     * @param usuario A entidade {@link Usuario} vinda do banco.
     * @return O {@link UsuarioDTO} correspondente.
     */
    private UsuarioDTO paraDTO(Usuario usuario) {
        return new UsuarioDTO(usuario);
    }

    /**
     * Converte um {@link DadosCadastroUsuarioDTO} (DTO) para a entidade
     * {@link Usuario} aplicando o hash na senha durante a conversão (RF06).
     *
     * @param dto {@link DadosCadastroUsuarioDTO} com os dados do novo usuário.
     * @return A entidade {@link Usuario} pronta para ser persistida.
     */
    private Usuario paraEntidade(DadosCadastroUsuarioDTO dto) {
        return new Usuario(dto.nome(), dto.email(), passwordEncoder.encode(dto.senha()));
    }

    /**
     * Tenta salvar uma entidade {@link Usuario} no repositório.
     * 
     * <p>
     * Este método encapsula o save() e trata a exceção de violação
     * de integridade (e-mail duplicado - RF04), lançando uma
     * exceção de negócio mais clara (ViolacaoDadosExcecao).
     *
     * @param usuario Entidade {@link Usuario} a ser salva.
     * @throws ViolacaoDadosExcecao Caso o e-mail (unique=true) já esteja
     *                              cadastrado.
     */
    @SuppressWarnings("null")
    private Usuario salvarEntidade(Usuario usuario) throws ViolacaoDadosExcecao {
        try {
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new ViolacaoDadosExcecao(
                    String.format("O e-mail: %s já está cadastrado.", usuario.getEmail()));
        }
    }
}
