package br.com.sfpacim.backend.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Teste de integração (API) para validar os endpoints do controlador.
 * <p>
 * Esta classe utiliza {@link SpringBootTest} para subir o contexto completo
 * da aplicação em uma porta aleatória e valida os endpoints
 * através de requisições HTTP reais usando REST Assured.
 *
 * @author Matheus F. N. Pereira
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OlaApiTest {

    /**
     * Injeta a porta aleatória em que o servidor foi iniciado.
     */
    @LocalServerPort
    private int port;

    /**
     * Configura o REST Assured antes de cada teste.
     * <p>
     * Define a porta base para a porta aleatória do servidor
     * e o caminho base para o prefixo global "/api".
     */
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api";
    }

    /**
     * Testa o endpoint GET /api/ola.
     * <p>
     * <b>Cenário:</b> Uma requisição GET real é feita para o endpoint "/ola".
     * <p>
     * <b>Resultado Esperado:</b>
     * <ul>
     * <li>O status da resposta deve ser 200 (OK).</li>
     * <li>O conteúdo do corpo da resposta deve ser a string "Olá Mundo!".</li>
     * </ul>
     */
    @Test
    void quandoChamarGetOla_deveRetornarStatus200EOlaMundo() {
        given()
                .when()
                .get("/ola")
                .then()
                .statusCode(200)
                .body(equalTo("Olá Mundo!"));
    }
}
