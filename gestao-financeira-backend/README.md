# Gestão Financeira Backend

## Finalidade do Projeto

Este projeto tem como objetivo fornecer uma API para controle financeiro pessoal, permitindo o gerenciamento de receitas, despesas, categorias, itens de compra e notas fiscais. Ele foi desenvolvido como parte do Trabalho de Conclusão de Curso (TCC) da Pós-Graduação da PUC, utilizando o framework Quarkus para backend em Java.

## Funcionalidades

- Cadastro e autenticação de usuários
- Gerenciamento de receitas e despesas
- Controle de categorias financeiras
- Registro e consulta de itens de compra
- Emissão e consulta de notas fiscais
- Integração com RabbitMQ para mensageria
- Documentação automática via Swagger UI

## Pré-requisitos

- Java 17 ou superior
- Maven 3.8.x ou superior
- Docker (opcional, para subir RabbitMQ e PostgreSQL)
- RabbitMQ rodando na porta padrão (`5672`)
- PostgreSQL rodando na porta padrão (`5432`)

## Instalação

1. **Clone o repositório:**

2. **Configure o banco de dados e RabbitMQ:**
   - Certifique-se de que o PostgreSQL e o RabbitMQ estejam rodando localmente.
   - As credenciais e URLs padrão estão configuradas em `src/main/resources/application.properties`.
   - Para subir via Docker:
     ```bash
     docker run --name postgres -e POSTGRES_USER=quarkus -e POSTGRES_PASSWORD=quarkus -e POSTGRES_DB=gestao_financeira -p 5432:5432 -d postgres:15
     docker run --name rabbitmq -p 5672:5672 -p 15672:15672 -d rabbitmq:management
     ```

3. **Compile o projeto:**
   ```bash
   ./mvnw clean package
   ```

4. **Execute o projeto em modo desenvolvimento:**
   ```bash
   ./mvnw quarkus:dev
   ```
   O projeto estará disponível em [http://localhost:8080](http://localhost:8080).

5. **Acesse a documentação da API:**
   - Swagger UI: [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

## Observações

- As configurações de conexão com banco de dados e RabbitMQ podem ser ajustadas no arquivo `src/main/resources/application.properties`.
- Para rodar em produção, utilize o comando:
  ```bash
  java -jar target/quarkus-app/quarkus-run.jar
  ```
- Para gerar um executável nativo:
  ```bash
  ./mvnw package -Dnative
  ```

## Licença

Este projeto é de uso acadêmico e está sob licença MIT.