# language: pt
Funcionalidade: Fluxo E2E de Saudação
  Para garantir que o frontend está conectado ao backend
  Eu, como usuário
  Quero ver a saudação "Olá Mundo!" na tela.

  Cenário: Usuário vê a mensagem da API na página inicial
    Dado que o navegador é inicializado
    E que o usuário acessa a aplicação
    Quando o frontend se comunica com o backend
    Então o texto "Olá Mundo!" deve estar visível na página