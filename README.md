# :100: People Score

>People Score é uma API REST para realização de cadastro de pessoas com score e suas regiões de afinidade.

A api foi construída com base no DDD (Domain Driven Design), e possui atualmente uma cobertura de código de 86%, com testes
cobrindo as camadas service, controller e repository (quando há querys customizadas).

### :pencil2: Premissas

Para o desenvolvimento desse serviço, foram consideradas as premissas abaixo:

* Usar a linguagem Java (preferência Java 11);
* Usar maven no build do projeto;
* Utilização do framework Spring;
* Montar banco de dados em memória (H2), usando Hibernate na persistência dos dados;
* Necessário pelo menos um teste unitário para cada método da camada Service, usando JUnit e Mockito;
* Documentar contratos REST usando Swagger;
* Colocar autenticação com token JWT.

### :dna: Lógica do serviço

* Na camada de serviços há a associação da região da afinidade com a região da pessoa, e o retorno da lista de estados correspondentes à região.
* Na camada de serviços foi feita a implementação para retornar o atributo scoreDescricao correspondente ao score encontrado entre <em>inicial</em> e <em>final</em>.
* Cadastro via POST dos seguintes dados na tabela Score:

  |scoreDescricao | inicial | final |
  |---------------|---------|-------|
  |Insuficiente   |0        |200    |
  |Inaceitável    |201      |500    |
  |Aceitável      |501      |700    |
  |Recomendável   |701      |1000   |

Obs: para a realização deste cadastro via POST, utilizou-se do EventListener <em>ApplicationReadyEvent</em> e do <em>Rest Template</em> para
que os dados acima fossem inseridos via POST automaticamente ao se iniciar a aplicação.


### :factory: Tecnologias Utilizadas

Tendo como base os requisitos e premissas acima, utilizou-se:

* [SpringBoot](https://spring.io/projects/spring-boot) para criar rapidamente um serviço REST de qualidade.
* [Mockito](https://site.mockito.org/) para simular e verificar comportamentos nos testes.
* [AssertJ](https://assertj.github.io/doc/) para tornar os testes unitários mais fluentes.
* [Caffeine](https://github.com/ben-manes/caffeine) para caching de requisições muito solicitadas e que possuem dados atualizados raramente,
como é o caso do método `findStateAbbreviationListByRegion`.
* [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) para fazer requisições http simples, como um POST, por exemplo.
* [Swagger](https://swagger.io/) utilizando a dependência springfox para documentação dos endpoints do serviço. Pode-se acessar a documentação ao iniciar o serviço e ir no endopoint ```[GET] /swagger-ui/index.html```
* OAuth2 e JWT, para realizar a autenticação do usuário e verificar a autorização para utilização dos endpoints.


### :test_tube: Como testar a aplicação?

Para testar, basta clonar o repositório, instalar as dependências e executar a aplicação via linha de comando ou utilizando o seu editor IntelliJ preferido.

O servidor está configurado para rodar na porta padrão 8080. E ao inicializar o serviço já é inserido um usuário admin. 
Logo, com a aplicação rodando, para se obter o token JWT pode-se enviar:

#### [POST] http://localhost:8080/oauth/token
Header: 
Autorização do tipo Basic Auth, com
```
username: peoplescore
password: peoplescore123

```
Body no formato x-www-form-urlencoded:
```
username: admin
password: 123456
grant_type: password
```
Dessa forma, você obterá o seu access_token no retorno da requisição.

### :movie_camera: Vídeo contendo o passo-a-passo acima e também um exemplo de utilização do token obtido passando como Bearer Token nos demais endpoints da aplicação

![20220713_014134](https://user-images.githubusercontent.com/50798315/178652940-c599df1e-2bd0-4858-abbf-fd01f8d14f28.gif)


### :octocat: Endpoints do serviço

> Abaixo estão os endpoints que requerem autenticação do usuário.

#### [POST] /pessoa
* Payload para adição de uma pessoa: 
```
{
    "nome": "Fulano de Tal",
    "telefone": "99 99999-9999",
    "idade": 99,
    "cidade": "XX",
    "score": 1000, //Entre 0 e 1000
    "regiao": "sudeste"
}
```
* Ao adicionar uma pessoa, além dos dados do POST, o atributo id e a data de inclusão são adicionados automaticamente.
* Retorna 201 no sucesso da inclusão.

#### [POST] /afinidade
* Payload para adição de uma afinidade:
```
{
    "regiao": "sudeste",
    "estados": [
        "SP",
        "RJ",
        "MG",
        "ES"
    ]
}
```
* Retorna 201 no sucesso da inclusão.

#### [POST] /score
* Payload para adição de um score:
```
{
    "scoreDescricao": "Insuficiente",
    "inicial": 0,
    "final": 200
}
```
* Retorna 201 no sucesso da inclusão.

#### [GET] /pessoa/{id}
* Retornando a seguinte estrutura de dados:
```
{
    "nome": "Fulano de Tal",
    "telefone": "99 99999-9999",
    "idade": 99,
    "scoreDescricao": "Recomendável",
    "estados": [
        "SP",
        "RJ",
        "MG",
        "ES"
    ]
}
```
* Se o id for encontrado no banco, retorna 200 OK com a estrutura de dados acima.
* Se o id não for encontrado, retorna 204 No Content.

#### [GET] /pessoa/
* Retorna uma lista de todo o cadastro, em que cada item possui a estrutura de dados abaixo:
```
[
    {
        "nome": "Fulano de Tal",
        "cidade": "Cidade de Fulano",
        "estado": "XX",
        "scoreDescricao": "Recomendável",
        "estados": [
            "SP",
            "RJ",
            "MG",
            "ES"
        ]
    },
    ...
]
```
* Se algum cadastro for encontrado no banco, retorna 200 OK, com a estrutura de dados acima.
* Se o id não for encontrado, retorna 204 No Content.


> Além dos endpoints implemenados acima, também tem-se os seguintes endpoints que não requerem autenticação:

#### [POST] /oauth/token
Para obtenção de um token de acesso aos endpoints acima.

* Authorization:
Type Basic Auth
username: peoplescore
password: peoplescore123

* Body:
Formato: x-www-form-urlencoded
username: admin
password: 123456
grant_type: password

#### [GET] /swagger-ui/index.html
Para visualização da documentação dos endpoints utilizando o Swagger.

