# 🌀 Sistema de Gerenciamento de Reservas - Condomínio

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue?logo=postgresql&logoColor=white)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

---

Este sistema foi desenvolvido para automatizar e organizar a reserva de espaços comuns (salões de festas, churrasqueiras, quadras) em um condomínio. A solução foca em segurança, integridade de dados e facilidade de uso para Síndicos, Gerentes e Moradores.

##  Tecnologias Utilizadas

* **Java 21** & **Spring Boot 4**
* **Spring Security** com autenticação **JWT** 
* **PostgreSQL** 
* **Hibernate/JPA** 
* **Docker & Docker Compose** 
* **Lombok** 
* **MapStruct** 
---

## 🛠️ Configuração do Ambiente (`.env`)

Para rodar o projeto, é necessário criar um arquivo `.env` na raiz do diretório para gerenciar as variáveis de ambiente sensíveis. 
Exemplo de conteúdo para o `.env`:

```env
# Configurações do Banco de Dados
DATABASE_HOST=db-reservas
DATABASE_PORT=5432
DATABASE_NAME=condominio_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=sua_senha_segura

# Segurança (JWT)
# Gere uma chave forte para a assinatura dos tokens
SECURITY_KEY=sua_chave_secreta_jwt_aqui
```

---

## 🐳 Execução com Docker

O projeto utiliza o **Docker Compose** para subir a aplicação e o banco de dados de forma orquestrada.

### Pré-requisitos
* Docker e Docker Compose instalados.

### Passo a Passo`
  **Subir os containers:**
    ```bash
    docker-compose up --build -d
    ```

A API estará disponível em `http://localhost:8080`. O banco de dados estará acessível internamente para a aplicação e externamente na porta `5432`.

---

## 🔐 Fluxo de Autenticação e Segurança

O sistema implementa um fluxo rigoroso de acesso:

1.  **Cadastro pelo Síndico:** O síndico cadastra o gerente, e o sistema gera uma senha provisória (UUID).
2.  **Primeiro Acesso:** O gerente tenta o login com a senha provisória. O sistema retorna `403 Forbidden` com a mensagem `TROCA_SENHA_OBRIGATORIA`.
4.  **Ativação:** Após a troca bem-sucedida, o usuário deve realizar o login novamente com a senha definitiva para receber o **JWT**.
5.  **Timezone:** A expiração do token está configurada para 15 minutos, ajustada para o fuso horário (UTC-3).



---

## 📡 Endpoints Principais

### Autenticação
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/login` | Autenticação inicial e obtenção de Token. |
| `POST` | `/auth/primeiro-acesso` | Atualização da senha provisória para definitiva. |
| `POST` | `/auth/refresh` | Solicitação de novo Access Token via Refresh Token. |

### Espaços (Locais)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/locais` | Cadastro de novo espaço (Restrito a role SINDICO). |
| `GET` | `/locais` | Listagem de todos os locais cadastrados. |

### Reservas
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/reservas` | Realiza reserva (Valida conflitos de data/horário). |
| `GET` | `/reservas` | Filtra reservas por data (`?data=yyyy-MM-dd`). |

---

## 📂 Estrutura do Docker Compose

```yaml
services:
 database:
    # Imagem oficial do Postgres:15
    # Persistência de dados configurada em volumes
  app:
    # Build da imagem Java baseada no Dockerfile
    # Consome variáveis do .env para configurar o DataSource
 
```

---
