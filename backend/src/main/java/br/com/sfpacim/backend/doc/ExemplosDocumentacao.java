package br.com.sfpacim.backend.doc;

/**
 * Classe utilitária (constante) para armazenar exemplos de JSON
 * usados na documentação do Swagger (OpenAPI).
 * 
 * <p>
 * Isso centraliza os exemplos e limpa as anotações nos Controladores.
 *
 * @author Matheus F. N. Pereira
 */
public final class ExemplosDocumentacao {

    // Previne a instanciação
    private ExemplosDocumentacao() {

    }

    /**
     * Exemplo de resposta para Erro 400 (Bad Request - E-mail duplicado).
     */
    public static final String ERRO_EMAIL_DUPLICADO = """
            {
              "status": 400,
              "titulo": "Violação de Dados",
              "mensagem": "O e-mail: matheus@email.com já está cadastrado.",
              "dataHora": "11/11/2025 08:00",
              "rota": "/api/autenticacao/cadastro"
            }
            """;

    /**
     * Exemplo de resposta para Erro 422 (Unprocessable Entity - Falha de
     * Validação).
     */
    public static final String ERRO_VALIDACAO_CADASTRO = """
            {
              "erro": {
                "status": 422,
                "titulo": "Erro de Validação",
                "mensagem": "Um ou mais campos estão inválidos.",
                "dataHora": "11/11/2025 08:00",
                "rota": "/api/autenticacao/cadastro"
              },
              "erros": [
                {
                  "campo": "senha",
                  "mensagem": "A senha deve ter no mínimo 8 caracteres, contendo ao menos uma letra maiúscula, uma minúscula e um número."
                },
                {
                  "campo": "email",
                  "mensagem": "O formato do e-mail é inválido."
                }
              ]
            }
            """;

    /**
     * Exemplo de resposta para Erro 500 (Internal Server Error).
     */
    public static final String ERRO_INTERNO_SERVIDOR = """
            {
              "status": 500,
              "titulo": "Erro Interno",
              "mensagem": "Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.",
              "dataHora": "11/11/2025 08:00",
              "rota": "/api/autenticacao/cadastro"
            }
            """;
}
