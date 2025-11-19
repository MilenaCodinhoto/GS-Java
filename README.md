# 🌿 MenteNeural — Plataforma de Bem-Estar Emocional no Trabalho

MenteNeural é uma aplicação desenvolvida para monitoramento emocional de colaboradores, permitindo que empresas acompanhem índices de humor, frequência de estresse e tendências emocionais — tudo de forma simples, intuitiva e humanizada.

Desenvolvido com **Java Spring Boot**, **Thymeleaf** e **Oracle Database** como parte da Global Solution FIAP.

---

## ✨ Funcionalidades

### 👤 Autenticação e Cadastro
- Login com e-mail corporativo e senha
- Cadastro de novos colaboradores
- Sessão persistente com HttpSession
- Validações completas

### 😊 Registro de Humor
- Cadastro de humor (1 a 5)
- Observações opcionais
- Data e hora geradas automaticamente
- Associação ao colaborador logado

### 📊 Dashboard Emocional
- Média dos últimos 30 dias
- Porcentagem de dias positivos
- Tendência dos últimos 7 dias
- Últimos registros
- Layout minimalista moderno

### 🧩 API REST
- `POST /api/humores`
- `GET /api/humores/colaborador/{id}`

---

## 🛠️ Tecnologias Utilizadas

- Java 21 (Amazon Corretto)
- Spring Boot 3
- Spring MVC + Thymeleaf
- Oracle SQL + SQL Developer
- Maven
- Postman
- HTML + CSS minimalista

---

## 📁 Estrutura do Projeto

```text
src/main/java/br/com/fiap
│
├── controller
│   ├── ui
│   └── api
│
├── dto
│
├── exception
│
├── model
│
├── repository
│
└── MenteNeuralApplication.java
