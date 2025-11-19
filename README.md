# 🌿 MenteNeural — Plataforma de Bem-Estar Emocional no Trabalho

**MenteNeural** é uma plataforma criada para auxiliar empresas no acompanhamento do bem-estar emocional de seus colaboradores.  
A aplicação permite registrar, visualizar e analisar tendências de humor de forma **intuitiva, humanizada e centrada no usuário**.

Este projeto foi desenvolvido como parte da **Global Solution FIAP**, utilizando **Java Spring Boot**, **Thymeleaf**, **Oracle Database** e princípios modernos de arquitetura e design.

---

## 🎯 Propósito do Projeto

A saúde mental no ambiente corporativo é um pilar fundamental para produtividade, segurança e clima organizacional.  
A MenteNeural foi idealizada para:

- Mapear flutuações emocionais no trabalho
- Entregar dados visuais de maneira simples
- Apoiar equipes de RH e liderança
- Promover ambientes mais saudáveis e acolhedores

Com um design minimalista inspirado em interfaces modernas, a plataforma busca ser leve, acolhedora e confortável de usar.

---

## ✨ Funcionalidades

### 👤 Autenticação & Onboarding
- Login por e-mail corporativo e senha
- Cadastro de novos colaboradores
- Seleção de empresa no cadastro
- Controle de sessão com HttpSession
- Tratamento de erros e mensagens amigáveis

---

### 😊 Registro de Humor
- Registro diário (1 a 5)
- Observação opcional
- Data/hora geradas automaticamente
- Associação automática ao colaborador logado
- Tela dedicada, simples e validada

---

### 📊 Dashboard Emocional

O Dashboard emocional exibe:

- 📌 **Média dos últimos 30 dias**
- 😊 **% de humor positivo**
- 🗂️ **Total de registros**
- 📈 **Tendência dos últimos 7 dias**
- 📝 **Histórico recente**
- Design minimalista e moderno

---

## 🧩 API REST Integrada

### Criar registro de humor
```http
POST /api/humores