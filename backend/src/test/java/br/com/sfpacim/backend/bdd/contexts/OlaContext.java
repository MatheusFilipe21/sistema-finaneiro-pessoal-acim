package br.com.sfpacim.backend.bdd.contexts;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

/**
 * Contexto de Teste para a funcionalidade de Saudação (Endpoint /api/ola).
 * <p>
 * Herda as configurações base de API e os métodos de verificação do
 * {@link BaseApiContext}.
 * Implementa apenas a lógica de chamada específica para a feature "Olá Mundo".
 *
 * @author Matheus F. N. Pereira
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OlaContext extends BaseApiContext {

    /**
     * Porta aleatória injetada pelo Spring onde o servidor de teste está rodando.
     */
    @LocalServerPort
    protected int porta;

    /**
     * Configura o RestAssured para usar a porta injetada e o path base e realiza a
     * chamada GET ao endpoint de saudação {@code /api/ola}.
     */
    public void chamarEndpointOla() {
        RestAssured.port = porta;
        RestAssured.basePath = "/api";

        this.resposta = given()
                .when()
                .get("/ola");
    }
}
