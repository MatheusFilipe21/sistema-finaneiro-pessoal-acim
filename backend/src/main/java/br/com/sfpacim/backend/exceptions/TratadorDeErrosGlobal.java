package br.com.sfpacim.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.sfpacim.backend.dtos.erro.ErroPadraoDTO;
import br.com.sfpacim.backend.dtos.erro.ErroValidacaoDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler Global de Exceções (@RestControllerAdvice).
 * 
 * <p>
 * Captura exceções específicas da aplicação e as transforma em
 * respostas HTTP (ResponseEntity) padronizadas (RF05).
 *
 * @author Matheus F. N. Pereira
 */
@Slf4j
@RestControllerAdvice
public class TratadorDeErrosGlobal {

    /**
     * Manipula exceções de violação de integridade dos dados (RF04 - E-mail
     * duplicado).
     *
     * @param excecao    A exceção de violação de dados capturada.
     * @param requisicao A requisição HTTP (para obter a Rota/URI).
     * @return ResponseEntity (HTTP 400) com o {@link ErroPadraoDTO}.
     */
    @ExceptionHandler({ ViolacaoDadosExcecao.class, DataIntegrityViolationException.class })
    public ResponseEntity<ErroPadraoDTO> excecaoViolacaoDados(Exception excecao, HttpServletRequest requisicao) {
        String mensagemErro;

        if (excecao instanceof ViolacaoDadosExcecao) {
            mensagemErro = excecao.getMessage();
        } else {
            mensagemErro = "Erro de integridade dos dados. Por gentileza, verifique os dados informados e tente novamente.";
        }

        ErroPadraoDTO erroPadrao = new ErroPadraoDTO(
                HttpStatus.BAD_REQUEST, // 400
                "Violação de Dados",
                mensagemErro,
                requisicao.getRequestURI());

        return ResponseEntity.badRequest().body(erroPadrao);
    }

    /**
     * Manipula exceções de validação (lançadas pelo @Valid no DTO).
     * Retorna HTTP 422 (Unprocessable Entity) e uma lista detalhada
     * dos campos que falharam na validação (RF02, RF03).
     *
     * @param excecao    A exceção {@link MethodArgumentNotValidException}
     *                   capturada.
     * @param requisicao A requisição HTTP (para obter a Rota/URI).
     * @return ResponseEntity contendo o DTO {@link ErroValidacaoDTO}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoDTO> excecaoValidacao(MethodArgumentNotValidException excecao,
            HttpServletRequest requisicao) {

        ErroValidacaoDTO erroValidacao = new ErroValidacaoDTO(
                HttpStatus.UNPROCESSABLE_ENTITY, // 422
                "Erro de Validação",
                "Um ou mais campos estão inválidos.",
                requisicao.getRequestURI());

        excecao.getFieldErrors().forEach(erroValidacao::adicionarErro);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroValidacao);
    }

    /**
     * Manipula exceções genéricas (não esperadas) do servidor.
     * Retorna um HTTP 500 (Internal Server Error) padronizado.
     *
     * @param excecao    A exceção genérica capturada.
     * @param requisicao A requisição HTTP (para obter a Rota/URI).
     * @return ResponseEntity (HTTP 500) com o {@link ErroPadraoDTO}.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadraoDTO> excecaoGenerica(Exception excecao, HttpServletRequest requisicao) {
        log.error("Erro inesperado no servidor: {}", excecao.getMessage(), excecao);

        ErroPadraoDTO erroPadrao = new ErroPadraoDTO(
                HttpStatus.INTERNAL_SERVER_ERROR, // 500
                "Erro Interno",
                "Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.",
                requisicao.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroPadrao);
    }
}
