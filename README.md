<h1 align="center">:file_cabinet: REST API Spring boot com MongoDB</h1>

## :memo: Resumo do projeto
Como objeto de estudo realizei diversas mudanças e incremento de funcionalidades nesse projeto. O original está com Spring Boot versão 2 e Java 8, foi criado pelo professor Nélio Alves, repo https://github.com/acenelio/workshop-springboot2-mongodb.

## :books: Funcionalidades originais do projeto com Spring Boot versão 2
* API implementa recursos de CRUD para a classe User e endpoint possui consultas básicas. Para os Posts a API permite consultar Posts por id, titulo e uma busca completa procurando um texto no titulo, corpo do post e nos comentários dos posts;
* Por meio de profiles do Spring é possível escolher qual ambiente usar (test, dev ou default), sendo que o de dev realiza uma carga inicial no banco de dados;
* Recursos explorados durante o projeto: Query Methods, @Query, Spring Data Mongo, Relacionamento por agregação, padrão DTO para proteger as classes de entidades, ResourceExceptionHandler para personalizar o tratamento de exceções e retorno das requisições.

## :books: Funcionalidades que eu implementei como forma de estudo e prática de Spring Boot
* Recursos CRUD para a classe Post e Comments, sendo possível adicionar posts e comentários via endpoint;
* Criei classe EnvironmentConfig para ler as credenciais do banco a partir de variáveis de ambiente ao invés de expor as senhas no application.properties;
* Adicionei OpenAI/Swagger para facilitar a documentação, teste e interação com os endpoints da API;
* Adicionei Paginação e ordenação em todos os métodos dos controllers que retornam uma lista e apliquei um limite máximo para o size afim de evitar sobrecarga no endpoint se a base tivesse milhões de registros;
* Implementei classe DTO para entidade Post;
* Criei testes de integração para o método findAll(Pageable) do controller User e Post usando Mockito. Até esse momento mantive o Spring Boot na versão 2 com Java 8;
* Atualizei a versão do Spring Boot para a versão 3.1, o OpenAPI-WebMVC-UI e tornei o projeto compatível com Java 17.

## :wrench: Tecnologias utilizadas
* Spring Boot Web
* Spring Data MongoDB
* SpringDoc OpenAPI WebMVC UI
* Java 17 (pode usar o Java 8 com um simples ajuste de dependência na classe ResourceExceptionHandler e alterando o pom.xml)

## :rocket: Executando o projeto

Pré-requisitos:
1. MongoDB - Instale o MongoDB ou execute um container Docker com MongoDB. Crie o database de nome workshop_mongo ou se quiser usar outro, ajuste o arquivo application.test.properties com o nome do banco que queira usar.
2. Java 17

Para executar o projeto faça o clone do repositório, no diretório onde executar esse comando será criado uma pasta com o nome posts-spring-mongo:
```
git clone https://github.com/EdsonSFreitas/posts-spring-mongo.git
```

Credenciais do banco: O projeto foi configurado para que o usuário e a senha de acesso ao banco estejam registradas apenas como variáveis de ambiente, portanto, crie as variáves de ambiente de nome "DB_USER" e "DB_PASS" no Sistema Operacional ou direto na IDE de sua escolha. Por exemplo, usando Linux basta executar esses dois comandos no terminal informando o usuario e senha do seu banco mongodb entre aspas duplas:
```
export DB_USER=""
export DB_PASS=""
```
Se não for necessário credenciais para conectar no seu mongodb altere o arquivo application-test.properties que contem esses campos para que não use as variáveis ${DB_USER} e ${DB_PASS} :
```
spring.data.mongodb.uri = mongodb://${DB_USER}:${DB_PASS}@localhost:27017/
spring.data.mongodb.database = workshop_mongo
```
Versão sem necessidade de credenciais de acesso ao banco:
```
spring.data.mongodb.uri = mongodb://localhost:27017/workshop_mongo
```

Compilação: Entre no diretório que contém o arquivo pom.xml e gere o artefato .jar com o maven:
```
cd posts-spring-mongo/posts/
mvn clean install -DskipTests
```
Execução: Se não ocorrer nenhum erro será gerado o arquivo posts-0.9.10-SNAPSHOT.jar, execute-o com:
```
java -jar posts-0.9.10-SNAPSHOT.jar
```

Com isso você terá acesso ao http://localhost:8080/swagger-ui/index.html para usar todos os endpoints disponíveis.

## :soon: Implementação futura
* Autenticação e Integração com JWT
* Mais testes unitários

## :dart: Status do projeto
* Em andamento
