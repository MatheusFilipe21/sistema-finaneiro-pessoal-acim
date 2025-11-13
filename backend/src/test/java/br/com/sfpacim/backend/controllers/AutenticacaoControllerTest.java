package br.com.sfpacim.backend.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sfpacim.backend.config.SegurancaConfig;
import br.com.sfpacim.backend.dtos.usuario.DadosCadastroUsuarioDTO;
import br.com.sfpacim.backend.dtos.usuario.UsuarioDTO;
import br.com.sfpacim.backend.exceptions.TratadorDeErrosGlobal;
import br.com.sfpacim.backend.services.UsuarioService;

/**
 * Testes unitários para a classe {@link AutenticacaoController}.
 * 
 * <p>
 * Esta classe utiliza {@link WebMvcTest} para carregar apenas a camada web
 * (MVC) e testa o controlador de forma isolada, simulando (mockando) o
 * UsuarioService.
 *
 * @author Matheus F. N. Pereira
 */
@WebMvcTest(AutenticacaoController.class)
@Import({ SegurancaConfig.class, TratadorDeErrosGlobal.class })
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    private static final String NOME = "Matheus Filipe do Nascimento Pereira";
    private static final String EMAIL = "matheusfnpereira@gmail.com";
    private static final String SENHA = "Ab123456";
    private static final String NOME_INVALIDO_BRANCO = " ";
    private static final String EMAIL_INVALIDO_FORMATO = "email.invalido.com";
    private static final String SENHA_INVALIDA_REGEX = "123456";

    /**
     * Testa o endpoint POST /autenticacao/cadastro (RF07).
     * Valida o cenário de sucesso.
     * 
     * <p>
     * Verifica se, ao enviar dados válidos, o controlador retorna HTTP 201
     * (Created),
     * o DTO do usuário no corpo e o cabeçalho 'Location'.
     *
     * @throws Exception se ocorrer um erro durante a execução do MockMvc.
     */
    @SuppressWarnings("null")
    @Test
    @DisplayName("cadastrar: Quando dados válidos, deve retornar HTTP 201 Created e o UsuarioDTO")
    void testeCadastrar_QuandoDadosValidos_DeveRetornar201() throws Exception {
        DadosCadastroUsuarioDTO dadosCadastro = new DadosCadastroUsuarioDTO(NOME, EMAIL, SENHA);
        UsuarioDTO usuarioCriado = new UsuarioDTO(UUID.randomUUID(), NOME, EMAIL);

        when(usuarioService.registrar(any(DadosCadastroUsuarioDTO.class))).thenReturn(usuarioCriado);

        String jsonRequisicao = objectMapper.writeValueAsString(dadosCadastro);

        mockMvc.perform(post("/autenticacao/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequisicao))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(usuarioCriado.id().toString()))
                .andExpect(jsonPath("$.nome").value(NOME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(header().exists("Location"));
    }

    /**
     * Testa a validação do DTO (RF04 - Campo em Branco).
     * 
     * <p>
     * Verifica se, ao enviar um nome que não atende ao @NotBlank,
     * o controlador (via @Valid e TratadorDeErros) retorna HTTP 422.
     *
     * @throws Exception se ocorrer um erro durante a execução do MockMvc.
     */
    @Test
    @DisplayName("cadastrar: Quando nome estiver em branco (DTO Validation), deve retornar HTTP 422")
    @SuppressWarnings("null")
    void testeCadastrar_QuandoNomeEmBranco_DeveRetornarUnprocessableEntity() throws Exception {
        DadosCadastroUsuarioDTO dadosInvalidos = new DadosCadastroUsuarioDTO(NOME_INVALIDO_BRANCO, EMAIL, SENHA);
        String jsonRequisicao = objectMapper.writeValueAsString(dadosInvalidos);

        mockMvc.perform(post("/autenticacao/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequisicao))
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Testa a validação do DTO (RF02 - E-mail Inválido).
     * 
     * <p>
     * Verifica se, ao enviar um e-mail que não atende ao @Email,
     * o controlador (via @Valid e TratadorDeErros) retorna HTTP 422.
     *
     * @throws Exception se ocorrer um erro durante a execução do MockMvc.
     */
    @Test
    @DisplayName("cadastrar: Quando e-mail for inválido (DTO Validation), deve retornar HTTP 422")
    @SuppressWarnings("null")
    void testeCadastrar_QuandoEmailInvalido_DeveRetornarUnprocessableEntity() throws Exception {
        DadosCadastroUsuarioDTO dadosInvalidos = new DadosCadastroUsuarioDTO(NOME, EMAIL_INVALIDO_FORMATO,
                SENHA);
        String jsonRequisicao = objectMapper.writeValueAsString(dadosInvalidos);

        mockMvc.perform(post("/autenticacao/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequisicao))
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Testa a validação do DTO (RF03 - Senha Fraca).
     * 
     * <p>
     * Verifica se, ao enviar uma senha que não atende ao @Pattern,
     * o controlador (via @Valid e TratadorDeErros) retorna HTTP 422.
     *
     * @throws Exception se ocorrer um erro durante a execução do MockMvc.
     */
    @Test
    @DisplayName("cadastrar: Quando senha for inválida (DTO Validation), deve retornar HTTP 422")
    @SuppressWarnings("null")
    void testeCadastrar_QuandoSenhaInvalida_DeveRetornarUnprocessableEntity() throws Exception {
        DadosCadastroUsuarioDTO dadosInvalidos = new DadosCadastroUsuarioDTO(NOME, EMAIL, SENHA_INVALIDA_REGEX);
        String jsonRequisicao = objectMapper.writeValueAsString(dadosInvalidos);

        mockMvc.perform(post("/autenticacao/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequisicao))
                .andExpect(status().isUnprocessableEntity());
    }
}
