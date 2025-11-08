# 💰 Sistema Financeiro Pessoal ACIM (SFP-ACIM)

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

## 🏗️ Arquitetura e Padrões

O projeto segue um modelo de **Monorepo** com separação clara de responsabilidades:

1.  **Estrutura de Repositórios:** `backend/`, `frontend/`, `e2e/`.
2.  **Qualidade:** JaCoCo, Jasmine (Cobertura) e SonarQube (Análise Estática).
3.  **Testes:** Implementação de uma Pirâmide de Testes Completa.

---

## 🧪 Estratégia de Testes (Quality Gate)

A qualidade é aplicada em três camadas:

- **Unitário/Slice (Backend):** Usando JUnit e MockMvc para testar a lógica dos **Repositórios, Serviços e Controladores** de forma isolada e rápida.
- **Integração/BDD (API):** Usando **Cucumber e Rest Assured** para validar o comportamento dos **fluxos de negócio** e a comunicação entre as camadas da API.
- **E2E (Ponta-a-Ponta):** Usando **Selenium e Behave (Python)** para automatizar os cenários Gherkin no navegador (Chrome Headless), validando a comunicação completa entre o Frontend e o Backend.

---

## 🚀 Pipeline de CI/CD (GitHub Actions)

O projeto é validado por uma pipeline de Integração Contínua (CI) definida em `.github/workflows/pipeline.yml`.

O pipeline é disparado automaticamente em `push` (para `main`/`develop`) ou `pull_request` (para `develop`) e executa três jobs sequenciais para garantir a qualidade do monorepo:

1.  **Job 1: Backend (Java):**

    - Compila o Spring Boot.
    - Roda `mvn verify` (JUnit, BDD/Cucumber).
    - Gera e armazena os relatórios (JaCoCo, Surefire).

2.  **Job 2: Frontend (Angular):**

    - Instala o Chrome Headless.
    - Roda `npm ci` e `npm test` (Karma/Jasmine).
    - Gera e armazena os relatórios (LCOV, JUnit XML).

3.  **Job 3: E2E e Quality Gate (Docker + Sonar):**
    - Espera os Jobs 1 e 2 terminarem com sucesso.
    - Inicia a aplicação completa (Postgres, Backend, Frontend) usando `docker compose up --build`.
    - Roda os testes E2E (Behave/Selenium) contra a aplicação containerizada.
    - (Se o E2E passar) Envia uma análise combinada (Java + TS) para o SonarQube Cloud para validar o Quality Gate.

---

## 💻 Setup do Ambiente de Desenvolvimento (DevContainer)

O ambiente está 100% configurado para VS Code/Docker.

1.  **Pré-requisitos:** Docker Desktop (ou Docker Engine) instalado e rodando.
2.  **VS Code:** Instale a extensão "Dev Containers".
3.  **Abrir:** Ao abrir a pasta no VS Code, o editor irá perguntar: **"Reopen in Container?"** Clique em Sim.

### Comandos Principais no VS Code (Run and Debug)

O fluxo de trabalho no VS Code é dividido em dois menus principais:

1.  **Menu "Run and Debug" (▶️):** Usado para iniciar **servidores** ou processos de **depuração** (debug).
2.  **Menu "Tasks" (Tarefas - `Ctrl+Shift+B`):** Usado para executar **scripts** que rodam e terminam (como builds ou testes).

| Tarefa                            | Descrição                                                                                       | Como Executar                                             |
| :-------------------------------- | :---------------------------------------------------------------------------------------------- | :-------------------------------------------------------- |
| **Backend (Iniciar)**             | Inicia a API Spring Boot (porta 8080).                                                          | Menu "Run and Debug" (▶️) -> **Spring Boot (Backend)**    |
| **Frontend (Iniciar)**            | Inicia o servidor Angular (porta 4200) com proxy para o backend.                                | Menu "Run and Debug" (▶️) -> **Angular (Frontend)**       |
| **Testes de Frontend**            | Roda os testes (Karma) em modo "watch" (observação) na porta 9876.                              | Menu "Run and Debug" (▶️) -> **Angular (Testes)**         |
| **Testes E2E (Debug)**            | Executa os testes Selenium/Python com o depurador anexado (permite breakpoints).                | Menu "Run and Debug" (▶️) -> **Selenium (E2E)**           |
| **Testes de Backend (Unitários)** | Roda/Depura testes unitários (JUnit) individualmente através da interface gráfica.              | **Aba "Testing" (🧪)** -> Selecionar e Rodar o teste.     |
| **Testes de Backend (Completo)**  | Roda `mvn clean verify`: testes JUnit, BDD (Cucumber), Rest Assured, e gera o relatório JaCoCo. | Menu `Terminal > Run Task...` -> **Spring Boot (Testes)** |

---

## 🐳 Ambiente de Produção Local (Docker Compose)

Para simular o ambiente de produção/deploy completo (com Nginx, JRE, etc.), você pode usar o `docker-compose.yml`.

**Este ambiente roda em portas distintas do ambiente de desenvolvimento (ex: 8081, 4201, 5433), garantindo que não haja conflito com as portas padrão (8080, 4200) usadas pelo DevContainer.**

1.  No seu terminal (na raiz do projeto), execute:
    ```bash
    docker compose up --build
    ```
    > **Observação (VS Code):** A extensão **"Containers"**, que já está no DevContainer, oferece atalhos visuais para esta operação. Ela adiciona um **Painel Containers** na barra lateral esquerda, permitindo que você inicie/pare/inspecione os containers.
2.  Acesse a aplicação em: **`http://localhost:4201`** (a porta 80 do Nginx é mapeada para a 4201).
3.  Para parar todos os serviços, pressione `Ctrl+C` ou rode:
    ```bash
    docker compose down
    ```

---

**(C) 2025 Autoria Coletiva: Alexandre Orlando Gracio, Catherine Marie Cavalcanti Aussourd, Ilka Fernanda Berenguer Paz, Matheus Filipe do Nascimento Pereira.**
Todos os direitos reservados. Este código não possui licença de código aberto e os direitos de propriedade intelectual são retidos pelos coautores. **Proibida a reprodução, distribuição ou qualquer uso comercial do software sem permissão expressa e por escrito dos autores.**
