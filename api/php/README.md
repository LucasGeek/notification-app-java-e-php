
<p align="center"><a href="https://laravel.com" target="_blank"><img src="https://raw.githubusercontent.com/laravel/art/master/logo-lockup/5%20SVG/2%20CMYK/1%20Full%20Color/laravel-logolockup-cmyk-red.svg" width="400" alt="Laravel Logo"></a></p>

## Executando o Projeto

Você pode executar este projeto de duas formas: com Docker ou sem Docker.

### Com Docker

1. Verifique se a porta 5432 não está sendo utilizada por outro serviço.
2. Execute o seguinte comando para iniciar os containers:

```bash
docker-compose up -d
```

3. Após os containers estarem em funcionamento, você pode acessar a documentação da API (Swagger) visitando:

```
http://127.0.0.1:8000/api/documentation
```

### Sem Docker

1. Crie o arquivo `.env` na raiz do projeto com a seguinte configuração de banco de dados:

```
DB_CONNECTION=pgsql
DB_HOST=localhost
DB_PORT=5432
DB_DATABASE=notification
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

2. Instale as dependências necessárias:

```bash
composer install
```

3. Gere a chave da aplicação:

```bash
php artisan key:generate
```

4. Execute as migrações de banco de dados:

```bash
php artisan migrate
```

5. Faça o cache dos arquivos de configuração:

```bash
php artisan config:cache
```

6. Finalmente, execute a aplicação:

```bash
php artisan serve
```

7. Você pode acessar a documentação da API (Swagger) visitando:

```
http://127.0.0.1:8000/api/documentation
```
