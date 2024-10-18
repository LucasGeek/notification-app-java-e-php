
<p align="center"><a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://user-images.githubusercontent.com/84719774/129191080-723b3b46-4e0b-4aa5-8eb9-654c2c025b18.png" width="400" alt="Spring Boot Logo"></a></p>

## Executando o Projeto

Você pode executar este projeto de duas formas: com Docker ou sem Docker.

### Sem Docker

1. Garanta que você tenha o Java 23 e Maven 3.9.9 instalados em sua máquina.

2. Edite o arquivo `application.yml` na raiz do projeto com a seguinte configuração de banco de dados:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/notification
    username: postgres
    password: postgres
    hikari:
      cachePrepStmts: true
      prepStmtCacheSize: 250
      prepStmtCacheSqlLimit: 2048
      useServerPrepStmts: true
```

3. Execute o seguinte comando para instalar as dependências e compilar o projeto:

```bash
mvn clean install
```

4. Finalmente, execute a aplicação:

```bash
mvn spring-boot:run
```

5. Você pode acessar a documentação da API (Swagger) visitando:

```
http://localhost:8080/swagger-ui/index.html
```

### Com Docker

1. Verifique se a porta 5432 não está sendo utilizada por outro serviço.

2. Execute o seguinte comando para iniciar os containers:

```bash
docker-compose up -d
```

3. Você pode acessar a documentação da API (Swagger) visitando:

```
http://localhost:8080/swagger-ui/index.html
```
