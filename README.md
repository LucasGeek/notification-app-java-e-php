
# Projeto API - Java e PHP

## Descrição

Este repositório contém dois projetos de API distintos, um desenvolvido em **Java** e outro em **PHP**, cada um utilizando as melhores práticas e padrões de suas respectivas linguagens e frameworks.

### Projeto Java

O projeto de API em Java foi desenvolvido utilizando a **Arquitetura Limpa (Clean Architecture)**, princípios do **SOLID** e **Domain-Driven Design (DDD)**. Esses conceitos garantem a escalabilidade, facilidade de manutenção e testabilidade do sistema, além de uma separação clara entre as camadas de negócio, aplicação e infraestrutura.

### Projeto PHP

O projeto de API em PHP foi desenvolvido com o framework **Laravel**, seguindo os padrões e convenções oferecidos pelo próprio framework. O Laravel facilita o desenvolvimento rápido, seguro e eficiente de APIs, com suporte a recursos como autenticação, rotas, migrações e muito mais.

## Docker

Ambos os projetos possuem arquivos `Dockerfile` e `docker-compose.yml` para facilitar a execução e configuração dos ambientes. Com o Docker, você pode levantar o ambiente de desenvolvimento ou produção rapidamente, sem necessidade de instalar as dependências diretamente no sistema.

### Como executar

#### Passos para ambos os projetos:

1. Certifique-se de ter o Docker e o Docker Compose instalados na sua máquina.
2. Clone o repositório para a sua máquina.
3. Acesse a pasta do projeto desejado (Java ou PHP).
4. Execute o comando a seguir para iniciar os containers:

   ```bash
   docker-compose up --build
   ```

5. Após o processo de build e subida dos containers, a API estará disponível para uso.

## Estrutura dos Projetos

### API - Java

- **Camada de Api**: Controladores que recebem as requisições HTTP.
- **Camada de Application**: Contém os casos de uso (application services) e orquestra as operações de negócio.
- **Camada de Domain**: Contém as entidades de domínio e as regras de negócio essenciais.
- **Camada de Infrastructure**: Implementa a persistência de dados e serviços externos, como integração com APIs ou bancos de dados.

### API - PHP (Laravel)

- **Routes**: Definição das rotas e endpoints da API.
- **Controllers**: Controladores responsáveis por receber as requisições e retornar as respostas apropriadas.
- **Models**: Representam as entidades e a lógica de negócio da aplicação.
- **Migrations**: Gerenciamento da estrutura do banco de dados, com versionamento de alterações.

## Contribuição

1. Faça um fork do projeto.
2. Crie uma branch para a sua feature (`git checkout -b minha-feature`).
3. Faça commit das suas alterações (`git commit -am 'Adiciona nova feature'`).
4. Faça push para a branch (`git push origin minha-feature`).
5. Abra um Pull Request.

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
