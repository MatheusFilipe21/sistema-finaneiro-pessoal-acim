package br.com.sfpacim.backend.bdd.steps;

import br.com.sfpacim.backend.bdd.contexts.OlaContext;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Definição dos passos (Steps) para a funcionalidade de Consulta de Saudação
 * (Feature Ola).
 * <p>
 * Esta classe traduz a linguagem Gherkin para a lógica de teste, injetando e
 * chamando o {@link OlaContext} para realizar as operações de API e validação.
 *
 * @author Matheus F. N. Pereira
 */
public class OlaSteps {

    @Autowired
    private OlaContext olaContext;

    /**
     * Step: "Quando o cliente faz uma requisição GET para "/api/ola""
     */
    @Quando("^o cliente faz uma requisição GET para \"/api/ola\"$")
    public void oClienteFazUmaRequisicaoGetParaApiOla() {
        olaContext.chamarEndpointOla();
    }

    /**
     * Step: "Então o status da resposta deve ser 200"
     *
     * @param codigoStatus O código de status (número) esperado para a resposta.
     */
    @Então("^o status da resposta deve ser (\\d+)$")
    public void oStatusDaRespostaDeveSer(int codigoStatus) {
        olaContext.verificarCodigoStatus(codigoStatus);
    }

    /**
     * Step: "E o corpo da resposta deve ser 'Olá Mundo!'"
     *
     * @param corpoEsperado O texto exato (String) esperado no corpo da resposta.
     */
    @Então("^o corpo da resposta deve ser \"(.*?)\"$")
    public void oCorpoDaRespostaDeveSer(String corpoEsperado) {
        olaContext.verificarCorpoResposta(corpoEsperado);
    }
}
