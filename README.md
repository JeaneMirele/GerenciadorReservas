# Sistema de Gerenciamento de Reservas - Condomínio

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue?logo=postgresql&logoColor=white)

---

Este sistema foi desenvolvido para organizar a reserva de espaços comuns (salões de festas, churrasqueiras, quadras) em um condomínio. A solução foca em segurança, integridade de dados e facilidade de uso para Síndicos, Gerentes e Moradores.

##  Tecnologias Utilizadas

* **Java 21** & **Spring Boot 3**
* **Spring Security** com autenticação **JWT** 
* **PostgreSQL** 
* **Hibernate/JPA** 
* **Docker & Docker Compose** 
* **Lombok** 
* **MapStruct** 
---

##  Configuração do Ambiente (`.env`)

Para rodar o projeto, é necessário criar um arquivo `.env` na raiz do diretório para gerenciar as variáveis de ambiente sensíveis. 
Exemplo de conteúdo para o `.env`:

```env
# Configurações do Banco de Dados
DATABASE_HOST=db-reservas
DATABASE_PORT=5432
DATABASE_NAME=condominio_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=senha_segura

# Segurança (JWT)
# Gere uma chave forte para a assinatura dos tokens
SECURITY_KEY=chave_secreta_jwt
```

Dica: Para gerar uma chave segura para o SECURITY_KEY, você pode executar o seguinte comando no seu terminal:

```Bash
openssl rand -base64 64
```
---

## Execução com Docker

A imagem está disponível no **Docker Hub**

### Pré-requisitos
* Docker e Docker Compose instalados.
* Arquivo `.env` configurado na raiz.

 **Suba os serviços:**
   ```bash
   docker-compose up -d
````
---
O Docker irá baixar automaticamente a imagem 986807/gerenciador-reservas:latest e configurar o banco de dados.

A API estará disponível em http://localhost:8080.

---

## Documentação da API (Swagger)
Com a aplicação rodando, você pode acessar a documentação interativa dos endpoints e testar as requisições diretamente pelo navegador:

URL: http://localhost:8080/swagger-ui/index.html

---

### Hierarquia de Acessos (Roles)

O sistema utiliza um modelo de permissões em cascata para garantir que cada usuário gerencie apenas o que lhe cabe:

| Papel (Role) | Responsabilidade | Criado por |
| :--- | :--- | :--- |
| **SINDICO** | Administrador total. Gerencia os espaços (locais), novos Síndicos e Gerentes. | Sistema / Outro Síndico |
| **GERENTE** | Operacional. Responsável pelo cadastro e gestão dos Moradores e supervisão de reservas. | Síndico |
| **MORADOR** | Usuário do condomínio. Pode visualizar locais e gerenciar suas próprias reservas. | Gerente |

## Fluxo de Autenticação e Segurança

O sistema implementa um fluxo rigoroso de acesso:

1.  **Cadastro pelo Síndico:** O síndico cadastra o gerente, e o sistema gera uma senha provisória (UUID).
2.  **login:** O gerente tenta o login com a senha provisória. O sistema retorna `403 Forbidden` com a mensagem `TROCA_SENHA_OBRIGATORIA`.
3.  **Troca de senha**:No primeiro acesso, o sistema solicita a troca de senha, requisitando a senha provisoria e a senha definitiva.
4.  **Ativação:** Após a troca bem-sucedida, o usuário deve realizar o login novamente com a senha definitiva para receber o **JWT**.
---

## Como Testar 
Para facilitar o teste inicial, o sistema possui um usuário Síndico, e um Gerente pré-cadastrado

Credenciais do Síndico:
````json
{
	"email": "sindico@sistema.com",
"senha":"sindico123"
}
````
Credenciais do Gerente:
````json
{
	"email": "gerente@sistema.com",
 "senha": "gerente123"
}
````

### Passo a Passo:

Faça login com o perfil Gerente 
- Endpoint: POST /auth/login

Copie o jwt e cole na requisição de criação de usuários
- Endpoint: POST /usuarios

Exemplo Body (JSON):

````json
{
    "nome": "Rafaela",
    "email": "morador@condominio.com",
    "cpf": "684.157.180-04",
    "telefone": "8493772772",
    "roles": ["MORADOR"]
}
````
  
2. Primeiro Acesso (Troca de Senha):
Como o usuário é novo, você deve primeiro trocar a senha provisória pela definitiva no endpoint de primeiro acesso para ativar a conta.

- Endpoint: POST /auth/primeiro-acesso

```json

{
  "email": "morador@condominio.com",
  "senhaProvisoria": "43kmr4",
  "novaSenha": "NovaSenhaSegura123"
}
````

#### 2. Login e Obtenção do Token (JWT):
Após a troca bem-sucedida, realize o login para receber o Token de acesso (JWT).

- Endpoint: POST /auth/login


````json
{
  "email": "morador@condominio.com",
  "senha": "SuaNovaSenhaSegura123"
}
````

#### 3. Autorizando no Swagger:

- Acesse o Swagger UI.

- No topo da página, clique no botão Authorize.

- No campo de valor, digite Bearer  seguido do token copiado (Exemplo: Bearer eyJhbG...).

- Clique em Authorize e depois em Close.

Nota: Agora todos os endpoints restritos à role MORADOR estarão liberados para teste.

## Endpoints
### 🔑 Guia de Endpoints por Perfil (RBAC)

| Categoria | Endpoint | Síndico | Gerente | Morador |
| :--- | :--- | :---: | :---: | :---: |
| **Autenticação** | `POST /auth/login` | ✅ | ✅ | ✅ |
| **Autenticação** | `POST /auth/primeiro-acesso` | ✅ | ✅ | ✅ |
| **Autenticação** | `POST /auth/refresh` | ✅ | ✅ | ✅ |
| **Usuários** | `POST /usuarios` (Criar novo) | ✅ | ✅ | ❌ |
| **Usuários** | `GET /usuarios` (Listar todos) | ✅ | ✅ | ❌ |
| **Usuários** | `GET /usuarios/email/{email}` | ✅ | ✅ | ❌ |
| **Usuários** | `PATCH /usuarios/meu-perfil/foto` | ✅ | ✅ | ✅ |
| **Usuários** | `DELETE /usuarios/{id}` | ✅ | ❌ | ❌ |
| **Locais** | `POST /locais` (Cadastrar Área) | ✅ | ❌ | ❌ |
| **Locais** | `GET /locais` (Ver Áreas Comuns) | ✅ | ✅ | ✅ |
| **Locais** | `PUT /locais/{id}` (Editar) | ✅ | ❌ | ❌ |
| **Locais** | `PATCH /locais/{id}/foto` | ✅ | ❌ | ❌ |
| **Reservas** | `POST /reservas` (Realizar Reserva) | ✅ | ✅ | ✅ |
| **Reservas** | `GET /reservas` (Filtrar por data) | ✅ | ✅ | ✅ |
| **Reservas** | `DELETE /reservas/{id}` (Cancelar) | ✅ | ✅ | ✅ |
| **Unidades** | `POST /unidades` (Cadastrar apto) | ✅ | ✅ | ❌ |
| **Unidades** | `GET /unidades` (Listar unidades) | ✅ | ✅ | ❌ |
| **Arquivos** | `GET /arquivos/{nome}` (Ver fotos) | ✅ | ✅ | ✅ |

