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

### Lógica do serviço

* Na camada service, há a associação da região da afinidade com a região da pessoa, e o retorno da lista de estados correspondentes à região.
* Na camada de serviços, foi feita a implementação para retornar o atributo scoreDescricao correspondente ao score encontrado entre <em>inicial</em> e <em>final</em>.
* Cadastro via POST dos seguintes dados na tabela Score:

  |scoreDescricao | inicial | final |
    |---------------|---------|-------|
  |Insuficiente   |0        |200    |
  |Inaceitável    |01       |500    |
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
* [Swagger](https://swagger.io/) para documentação dos endpoints do serviço.
* OAuth2 com JWT, para realizar a autenticação do usuário e verificar a autorização para utilização dos endpoints.

### :octocat: Endpoints do serviço

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
    "regiao": "sudeste",
    "estados": [
        "SP",
        "RJ",
        "MG",
        "ES"
    ]
```
* Retorna 201 no sucesso da inclusão.

#### [POST] /score
* Payload para adição de um score:
```
    "scoreDescricao": "Insuficiente",
    "inicial": 0,
    "final": 200
```
* Retorna 201 no sucesso da inclusão.

#### [GET] /pessoa/{id}
* Se o id for encontrado no banco, retorna a seguinte estrutura de dados:
```
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
```
* Se o id for encontrado no banco, retorna 200 OK, com a estrutura de dados.
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
