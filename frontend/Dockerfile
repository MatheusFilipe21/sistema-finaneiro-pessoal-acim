# ===================================================================
# ETAPA 1: BUILD (Compilação dos Arquivos Estáticos do Angular)
# ===================================================================
# Usa a imagem oficial do Node (Alpine é leve)
FROM node:22-alpine AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de configuração (para otimização de cache do npm ci)
COPY package.json package-lock.json ./
COPY angular.json .
COPY tsconfig.json .
COPY tsconfig.app.json .
COPY tsconfig.spec.json .

# Instala as dependências de forma limpa (otimizado para CI)
RUN npm ci

# Copia o código-fonte e a pasta public
COPY src ./src
COPY public ./public

# Define o argumento para o caminho de saída (output) do build
ARG ANGULAR_OUTPUT_PATH=dist/frontend

# Executa o build de produção do Angular
RUN npm run build -- --output-path "/app/$ANGULAR_OUTPUT_PATH"

# ===================================================================
# ETAPA 2: RUNTIME (Servir com Nginx Unprivileged)
# ===================================================================
# Usa a imagem Nginx Unprivileged (resolve permissões)
FROM nginxinc/nginx-unprivileged:alpine

# Usa o utilizador não-root 'nginx'
USER nginx

# Copia o arquivo de configuração principal do Nginx
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf

# Redeclara o argumento para ser acessível nesta etapa
ARG ANGULAR_OUTPUT_PATH=dist/frontend

# Copia os arquivos de build (do subdiretório /browser/) da etapa 'build'
# e define 'nginx' como proprietário
COPY --chown=nginx:nginx --from=build /app/$ANGULAR_OUTPUT_PATH/browser/ /usr/share/nginx/html

# Expõe a porta 80 (padrão do Nginx)
EXPOSE 80

# Comando de entrada para executar o Nginx em modo foreground (primeiro plano)
CMD ["nginx", "-g", "daemon off;"]
