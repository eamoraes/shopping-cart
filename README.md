# Shopping Cart API
Solução desenvolvida para o cadastro de carrinho de compras.

## Pré-Requisitos
Para executar o projeto é necessário ter instalado e configurado:
* [JDK][] 8+
* [Git][]
* [Maven][] 

## Obtendo o projeto
Projeto disponível para download no endereço https://github.com/eamoraes/shopping-cart. 

Para clonar o projeto utilizar o comando abaixo em um diretório:
>git clone https://github.com/eamoraes/shopping-cart.git

## Executando o projeto
Depois de efetuar o download/clone do projeto, com o maven devidamente configurado, acessar o diretório shopping-cart e dentro dele executar o comando mvn spring-boot:run.

A aplicação ficará disponível para consumo no endereço http://localhost:8080/.

## Documentação
Para consulta dos endpoints disponíveis para consumo, acessar a documentação do Swagger em http://localhost:8080/swagger-ui.html, onde cada endpoint também poderá ser testado.

### Postman
A API também poderá ser testada via Postman, com a collection Shopping Cart.postman_collection.json disponível no diretório /etc/postman/.

[Git]: https://git-scm.com/ "Git"
[JDK]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[Maven]: http://maven.apache.org/ "Maven"

## Stack utilizada
* JDK 8
* Spring boot 2.1.8
* Lombok
* Spring Test
* Mockito
* Jetty Webserver
* Maven
* H2 Database
* Swagger

### Estratégia adotada na solução 
Estratégias utilizadas na criação da API REST

Foi desenvolvida, utilizando o padrão REST, diversos endpoints para o consumo de uma aplicação externa. Atualmente a API conta com endpoints para o CRUD dos seguintes itens:
* Cadastro de Itens
* Cadastro de Usuários

Tendo realizado o cadastro de itens e usuários é possível utilizar os endpoints para montagem do carrinho de compras, onde é possível adicionar, remover e fechar a compra.

Todos endpoints retornam um objeto de resposta padrão, onde são retornados os dados, uma lista de erros caso ocorra e o respectivo código de status HTTP, em acordo com o padrão REST. Foi criada uma classe Advice para manipular alguns tipos de exceções e colocá-las nesse padrão.

A maioria das validações foram possíveis de realizar usando as anotações do Java Bean Validation, como por exemplos, campos que não podiam ser vazios e o valor do item que não poderia ser <= 0. Para a validação do usuário único, foi considerado o campo email como o campo a ser comparado.

Para o método fechar compras, onde os itens precisavam ser ordenados por nome e valor foi utilizado a sintaxe do Spring Data JPA, assim como para todas as consultas ao banco. 
Foi criado um objeto DTO para montagem desse relatório, onde foi criada uma lista e cada item foi adicionado com seu respectiva quantidade e valor. Ao final foi adicionado um totalizador com a quantidade total de itens e o valor total do carrinho de compras.

Foram feitos testes de integração para todos os endpoints, onde foram utilizados o Spring Test e o Mockito.

A documentação da API foi feita utilizando o Swagger, onde é possível ver todos os endpoints disponíveis para consumo.

Para facilitar os testes da API, foi adicionado um arquivo import.sql. Neste arquivo contém o script de inserção para um usuário e dois itens.
