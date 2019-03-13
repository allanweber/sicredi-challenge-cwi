# Sicredi challenge

## Estrutura
O projeto é composto por:

### Api
* domain: entidades, DTO, assinaturas de serviços, exceções e etc.
* infrastructure: serviços concretos, repositórios
* config: spring, mongo, swagger e exception handlers configurações
* application.api: apis rest para consumir a aplicação

### Front
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

# Explicação breve do porquê das escolhas tomadas durante o desenvolvimento da solução

* Usei a **stack e libs** citadas porque Java com Sring é meu foco de carreira e acredito serem simples para implementar uma solução.  
* A utilização do **mongo** foi uma opção de baixo custo de processamento e de programação já que um banco relacional não seria o ideal para essa solução.  
* **Model mapper** para não expor as entidades da api e mapear para um Dto.
* **Apache commons** lang tem algumas apis uteis para formatação e validação.
* **Swagger** que ao meu ver é a melhor forma de documentar Apis rest.
* **Angular** é meu framework front end favorito e que tenho mais intimidade, não usei o tempo do teste para criar uma interface mirabolante e cheia de usabilidade até porque não sou um frontend developer muito experiente, apesar de me considerar full stack, entretanto, apesar do design simples, tentei criar uma interface facil de usar e com alguma beleza, para isso usei o **Bulma** um framework CSS open source e baseado em Flexbox.

## Débitos no desafio
* **Tarefa Bônus 2 - Mensageria e filas**: não fiz pois alem do tempo, por algum motivo não consegui subir um container com rabbitMQ local, mas usaria ele para postar numa exchange uma mensagem de votação expirada com o resultado desta, o producer dessa fila seria chamado no **ExpireJob** junto com o Schedule de votações expiradas.
* **Tarefa Bônus 3 - Performance**: teria que avaliar como contornar essa questão, talvez usando filas também, para processar a mensagem quando possível. Não fiz os testes de performance.
* **Tarefa Bônus 4 - Versionamento da API**: já criei as apis como V1, criaria outro contoller para cada versão nova necessária e implementaria nele as alterações, o path ficaria ****/vx/****, por exemplo **/v2/**
* **Mensagens e organização dos commits**: só vi esse requisito no momento de postar o resultado do desafio, então acabou saindo apenas um commit, em equipe costumo commitar grupos pequenos de arquivos e dividir bem granular as tasks para que sejam pequenas e possam ser enviadas para PRD conforme a task/storia fica pronta e passa em testes, pipiline, homologação e etc.

## Melhorias
* Melhorar/aplicar mais logs
* Autenticação
* Teste de mutação com Pitest por exemplo

# Run the app

**Precisa ter o docker instalado ou uma instancia de mongo que:**
* Ou seja local acessível por localhost:27017
* Ou a configuração de conexão da api deve ser alterada no arquivo application.yml

## Executrar as aplicações

* Se optar por criar uma instancia de mongo com docker, rodar o comando: **docker run -p 27017:27017 mongo**, pode usar **-d** para deixar detached
* Ir para o diretório api e rodar o comando para subir a aplicação: **gradlew bootRun**
* Ir para o diretório front e rodar o comando para subir a aplicação: **ng serve**
* Acessar a url **http://localhost:8080** ou swagger **http://localhost:8080/swagger-ui.html**
* Acessar a url **http://localhost:4200** para o front end

**Para testar o projeto de api**

* Ir para o diretório api e rodar o comando :  **gradlew test**

**Para usar os endpoints da api**

* acessar o swagger **http://localhost:8080/swagger-ui.html** onde as apis estão documentadas
