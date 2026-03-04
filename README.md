# Sistema Gerenciador de Reservas 🏢

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?logo=springboot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-17+-red?logo=angular&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue?logo=postgresql&logoColor=white)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

---
Este é um projeto **Monorepo** em que o objetivo é facilitar a gestão e reserva de áreas comuns em condomínios, como quadras poliesportivas, churrasqueiras e salões de festas.

## Tecnologias Utilizadas

### Backend
* **Linguagem:** Java 21 
* **Framework:** Spring Boot 4.0.3
* **Segurança:** Spring Security 
* **Banco de Dados:** PostgreSQL
* **Gerenciador de Dependências:** Maven

### Frontend
* **Framework:** Angular
* **Linguagem:** TypeScript

---

## 🏗️ Estrutura do Projeto 

O projeto está organizado para separar as responsabilidades de cada serviço:

```text
sistema-gerenciador-reservas/
├── backend/    # API REST em Spring Boot
├── frontend/   # Interface do usuário em Angular
└── README.md   # Documentação do projeto
```
## Funcionalidades Principais

* **Gestão de Espaços:** Cadastro e visualização de locais disponíveis para aluguel (churrasqueiras, quadras, etc.).
* **Controle de Usuários e Perfis:** Implementação de diferentes níveis de acesso utilizando Spring Security:
* **ADMIN:** Gerencia moradores, espaços e todas as reservas.
* **MORADOR:** Realiza e acompanha suas próprias reservas.
* **Sistema de Reservas:** Lógica para evitar conflitos de datas e horários nos espaços comuns.

## 🛠️ Como Executar o Projeto

### Pré-requisitos

* Java 21 instalado.
* Node.js e Angular CLI instalados.
* PostgreSQL configurado.

### Passos para rodar localmente

* Clonar o repositório:

git clone [https://github.com/JeaneMirele/GerenciadorReservas.git](https://github.com/JeaneMirele/GerenciadorReservas.git)

#### Configurar o Backend:

* Navegue até cd backend.
* Ajuste o application.properties com suas credenciais do Postgres.
* Execute: ./mvnw spring-boot:run.

#### Configurar o Frontend:

* Navegue até cd frontend.
* Execute: npm install e depois ng serve.
* Acesse: http://localhost:4200.
