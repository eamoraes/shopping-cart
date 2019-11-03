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
