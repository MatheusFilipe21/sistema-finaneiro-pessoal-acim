# language: pt
Funcionalidade: Consulta de Saudação Inicial
  Para validar a saúde básica da API e a integração Frontend/Backend
  Eu, como usuário ou administrador,
  Quero consultar o endpoint de saudação
  Para receber a mensagem "Olá Mundo!".

  Cenário: Consulta Simples à Saudação
    Quando o cliente faz uma requisição GET para "/api/ola"
    Então o status da resposta deve ser 200
    E o corpo da resposta deve ser "Olá Mundo!"