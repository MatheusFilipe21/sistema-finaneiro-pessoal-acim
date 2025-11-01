package br.com.sfpacim.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste de "fatia" (slice test) para o {@link OlaControlador}.
 * <p>
 * Esta classe utiliza {@link WebMvcTest} para carregar apenas a camada web
 * (MVC) e testa o controlador de forma isolada, usando {@link MockMvc}.
 *
 * @author Matheus F. N. Pereira
 */
@WebMvcTest(OlaControlador.class)
class OlaControladorTest {

    /**
     * Objeto Mock injetado para simular requisições HTTP
     * sem a necessidade de um servidor web real.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Testa o endpoint GET /ola.
     * <p>
     * <b>Cenário:</b> Uma requisição GET é feita para o endpoint "/ola".
     * <p>
     * <b>Resultado Esperado:</b>
     * <ul>
     * <li>O status da resposta deve ser 200 (OK).</li>
     * <li>O conteúdo do corpo da resposta deve ser a string "Olá Mundo!".</li>
     * </ul>
     * <p>
     * <b>Nota:</b> O prefixo global "/api" (do context-path) não é
     * considerado pelo MockMvc em um {@code @WebMvcTest},
     * portanto, testamos o mapeamento direto do controlador ({@code /ola}).
     *
     * @throws Exception se ocorrer um erro durante a execução do MockMvc.
     */
    @Test
    void quandoChamarGetOla_deveRetornarStatus200EOlaMundo() throws Exception {
        mockMvc.perform(get("/ola"))
                .andExpect(status().isOk())
                .andExpect(content().string("Olá Mundo!"));
    }
}
