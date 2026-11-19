# Fluxo — API de Monitoramento de Ocupação

**FIAP \| Análise e Desenvolvimento de Sistemas \| Global Solution
2026/2 \| Java Advanced**

## Integrantes

| Nome completo            | RM     |
|--------------------------|--------|
| Mateus Ribeiro Azevedo   | 566630 |
| Paulo Gustavo Moraes     | 568580 |
| Luana Alves de Oliveira  | 566621 |
| Isabely Marques da Silva | 566663 |

## Sobre o projeto

O Fluxo é uma API REST voltada ao monitoramento da ocupação de estações
de metrô. A solução registra entradas e saídas de passageiros, calcula a
ocupação das estações e gera alertas automaticamente quando a capacidade
ultrapassa 85%, auxiliando na gestão do fluxo de pessoas.

## Funcionalidades

- Cadastro, consulta, atualização e exclusão de linhas e estações.
- Registro de ocupações e consulta ao histórico por estação e período.
- Alertas automáticos nos níveis moderado, alto e crítico.
- Autenticação JWT e controle de acesso por perfil ADMIN e OPERADOR.
- Paginação, ordenação, Query Methods, JPQL, Specification, Cache e
  HATEOAS.
- Documentação Swagger, monitoramento Actuator e configuração CORS.
- Bean Validation e tratamento global de exceções (parcial).

## Tecnologias

Java 25, Spring Boot 4.1.2, Spring Data JPA, Hibernate, Spring Security,
JWT, H2, Maven, Swagger/OpenAPI e Postman.

## Como executar

1.  Utilize o JDK 25 e o Maven.
2.  Configure a variável de ambiente `JWT_SECRET` com uma chave própria
    de pelo menos 32 bytes.
3.  Na raiz do projeto, execute `mvn spring-boot:run`.
4.  Acesse a documentação em [Swagger
    UI](http://localhost:8080/swagger-ui/index.html).

O banco H2 é executado em memória; os dados são perdidos ao reiniciar a
aplicação.

### Autenticação para demonstração

Realize `POST /autenticacao/login` utilizando Basic Auth e utilize o
token retornado como Bearer Token nas consultas protegidas.

| Usuário  | Senha de demonstração | Perfil   |
|----------|-----------------------|----------|
| admin    | 123                   | ADMIN    |
| operador | 456                   | OPERADOR |

Credenciais destinadas exclusivamente ao ambiente acadêmico local.

## Principais endpoints

| Endpoint             | Finalidade                       |
|----------------------|----------------------------------|
| `/linhas`            | Gerenciamento de linhas          |
| `/estacoes`          | Gerenciamento de estações        |
| `/estacoes/filtrar`  | Filtros dinâmicos                |
| `/ocupacoes`         | Registro e consulta de ocupações |
| `/ocupacoes/periodo` | Histórico por período            |
| `/alertas`           | Consulta de alertas automáticos  |
| `/actuator/health`   | Monitoramento da aplicação       |

------------------------------------------------------------------------

Projeto acadêmico desenvolvido para a Global Solution da FIAP.
