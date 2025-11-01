package br.com.sfpacim.backend.bdd.contexts;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe base abstrata para todos os Contextos de Teste de API.
 * <p>
 * Centraliza a configuração do Spring Boot, a injeção da porta
 * aleatória, o estado da resposta e os métodos de verificação básicos.
 * Todas as classes de Contexto API devem herdar desta classe.
 *
 * @author Matheus F. N. Pereira
 */
public abstract class BaseApiContext {

    /**
     * Armazena a resposta HTTP recebida do endpoint da API.
     */
    protected Response resposta;

    /**
     * Retorna o código de status HTTP da última resposta da API.
     *
     * @return O código de status HTTP (ex: 200, 404).
     */
    public int getCodigoStatus() {
        return resposta.getStatusCode();
    }

    /**
     * Retorna o corpo da última resposta da API como uma {@code String}.
     *
     * @return O corpo da resposta (body).
     */
    public String getCorpoResposta() {
        return resposta.getBody().asString();
    }

    /**
     * Verifica se o código de status da última resposta corresponde ao valor
     * esperado.
     *
     * @param codigoStatusEsperado O código de status a ser verificado.
     */
    public void verificarCodigoStatus(int codigoStatusEsperado) {
        assertEquals(codigoStatusEsperado, this.getCodigoStatus());
    }

    /**
     * Verifica se o corpo da última resposta corresponde ao corpo esperado.
     *
     * @param corpoEsperado O corpo da resposta (String) a ser verificado.
     */
    public void verificarCorpoResposta(String corpoEsperado) {
        assertEquals(corpoEsperado, this.getCorpoResposta());
    }
}
