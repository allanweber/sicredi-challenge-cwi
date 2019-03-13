# Sicredi challenge

## Estrutura
O projeto é composto por:

### Api
* domain: entidades, DTO, assinaturas de serviços, exceções e etc.
* infrastructure: serviços concretos, repositórios
* config: spring, mongo, swagger e exception handlers configurações
* application.api: apis rest para consumir a aplicação

### App
* Aplicações frontend em angular 7

## Stack
* Java
* Angular 7
* MongoDb + Spring Starter Mongo
* Spring Boot/Stater
* JUnit + Spring Boot Test
* swagger + swagger-ui
* Model Mapper
* Apache commons lang

## Melhorias
* Melhorar/aplicar mais logs
* Autenticação
* Teste de mutação com Pitest por exemplo

# Run the app

**Precisa ter o docker instalado ou uma instancia de mongo que:**
* Ou seja local acessível por localhost:27017
* Ou a configuração de conexão da api deve ser alterada no arquivo application.yml

## Executrar as aplicações

* Se optar por criar uma instancia de mongo com docker, rodar o comando: **docker run -p 27017:27017 mongo**, pode user -d para deixar detached
* Ir para o diretório api e rodar o comando para subir a aplicação: **gradlew bootRun**
* Ir para o diretório app e rodar o comando para subir a aplicação: **ng serve**
* access the url **localhost:8080** or swagger **http://localhost:8080/swagger-ui.html**

**Para testar o projeto de api**

* Ir para o diretório api e rodar o comando :  **gradlew test**

**Para usar os endpoints da api**

* acessar o swagger **http://localhost:8080/swagger-ui.html** onde as apis estão documentadas
