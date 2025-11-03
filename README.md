# Sistema Financeiro Pessoal ACIM (SFP-ACIM)

O SFP-ACIM é uma plataforma modular de gestão financeira pessoal desenvolvida para oferecer controle detalhado sobre receitas e despesas. Este projeto utiliza uma arquitetura moderna de monorepo.

---

## 🛠️ Stack Tecnológica

Nosso monorepo é construído com as seguintes tecnologias principais:

| Componente          | Tecnologia                 | Versão   | Propósito                                                |
| :------------------ | :------------------------- | :------- | :------------------------------------------------------- |
| **Backend**         | Java / Spring Boot         | 21 / 3.x | API de negócios (Controladores, Serviços, Persistência). |
| **Frontend**        | Angular                    | v20      | Interface de usuário web.                                |
| **Database**        | PostgreSQL                 | 18       | Armazenamento de dados.                                  |
| **Testes E2E**      | Python / Selenium / Behave | 3.12     | Validação de fluxo ponta-a-ponta (BDD).                  |
| **Containerização** | Docker Compose             |          | Ambiente de desenvolvimento local e CI/CD.               |

## Arquitetura e Padrões

O projeto segue um modelo de **Monorepo** com separação clara de responsabilidades:

1.  **Estrutura de Repositórios:** `backend/`, `frontend/`, `e2e/`.
2.  **Qualidade:** JaCoCo, Jasmine (Cobertura) e SonarQube (Análise Estática).
3.  **Testes:** Implementação de uma Pirâmide de Testes Completa.

---

## Estratégia de Testes (Quality Gate)

A qualidade é aplicada em três camadas:

- **Unitário/Slice (Backend):** Usando JUnit e MockMvc para testar a lógica dos **Repositórios, Serviços e Controladores** de forma isolada e rápida.
- **Integração/BDD (API):** Usando **Cucumber e Rest Assured** para validar o comportamento dos **fluxos de negócio** e a comunicação entre as camadas da API.
- **E2E (Ponta-a-Ponta):** Usando **Selenium e Behave (Python)** para automatizar os cenários Gherkin no navegador (Chrome Headless), validando a comunicação completa entre o Frontend e o Backend.

## Setup do Ambiente de Desenvolvimento (DevContainer)

O ambiente está 100% configurado para VS Code/Docker.

1.  **Pré-requisitos:** Docker Desktop (ou Docker Engine) instalado e rodando.
2.  **VS Code:** Instale a extensão "Dev Containers".
3.  **Abrir:** Ao abrir a pasta no VS Code, o editor irá perguntar: **"Reopen in Container?"** Clique em Sim.

### Comandos Principais no VS Code (Run and Debug)

| Tarefa                 | Descrição                                                        | Como Executar                                            |
| :--------------------- | :--------------------------------------------------------------- | :------------------------------------------------------- |
| **Backend**            | Inicia a API Spring Boot (porta 8080).                           | Menu "Run and Debug" (▶) -> **Spring Boot (Backend)**    |
| **Frontend**           | Inicia o servidor Angular (porta 4200) com proxy para o backend. | Menu "Run and Debug" (▶) -> **Angular (Frontend)**       |
| **Testes de Backend**  | Roda testes JUnit, BDD, Rest Assured, e gera o relatório JaCoCo. | Menu "Run and Debug" (▶) -> **Backend (Testes - Maven)** |
| **Testes de Frontend** | Roda testes Jasmine/Karma com Chrome Headless e gera cobertura.  | Menu "Run and Debug" (▶) -> **Angular (Testes)**         |
| **Testes E2E**         | Executa os testes de integração ponta-a-ponta (BDD).             | Menu "Run and Debug" (▶) -> **Selenium (E2E)**           |

---

**(C) 2025 Autoria Coletiva: Alexandre Orlando Gracio, Catherine Marie Cavalcanti Aussourd, Ilka Fernanda Berenguer Paz, Matheus Filipe do Nascimento Pereira.**
Todos os direitos reservados. Este código não possui licença de código aberto e os direitos de propriedade intelectual são retidos pelos coautores. **Proibida a reprodução, distribuição ou qualquer uso comercial do software sem permissão expressa e por escrito dos autores.**
