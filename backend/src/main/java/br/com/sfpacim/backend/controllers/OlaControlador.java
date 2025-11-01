package br.com.sfpacim.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST inicial para validação da API ("Hello World").
 * <p>
 * Os endpoints deste controlador são mapeados sob o prefixo global "/api",
 * definido no {@code application.yaml} (server.servlet.context-path).
 *
 * @author Matheus F. N. Pereira
 */
@RestController
public class OlaControlador {

    /**
     * Endpoint que retorna uma string "Olá Mundo!".
     * <p>
     * Responde a requisições GET no caminho {@code /api/ola}.
     *
     * @return {@link ResponseEntity} com status 200 (OK) e a string "Olá Mundo!" no
     *         corpo.
     */
    @GetMapping("/ola")
    public ResponseEntity<String> dizerOla() {
        return ResponseEntity.ok("Olá Mundo!");
    }
}
