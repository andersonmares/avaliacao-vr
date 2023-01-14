# Desafio VR
Etapa qualificatória da empresa VR Benefícios

## Requisitos para executar o projeto
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [JDK 11+](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
- [Postman](https://www.postman.com/)

# Como executar o projeto
- Clone o projeto.
```bash
  git clone https://github.com/andersonmares/avaliacao-vr.git
```

- Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

- Abra um terminal na raiz do projeto e a pasta docker.
```bash
  cd docker
```

- Agora execute o comando abaixo para iniciar o banco de dados Mysql no docker.
```bash
  docker-compose up -d
```

- Execute o arquivo com a classe main DesafioVrApplication.java ou execute o comando abaixo em um terminal na raiz do projeto
```bash
  mvn spring-boot:run
```

- Acesse os endpoints através do swagger usando o endereço local http://localhost:8080 ou utilize o [Postman](https://www.postman.com/)


# Endpoins do Sistema

## Cartão
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /cartoes | Cria um novo cartão |
| GET  |  /cartoes/{numeroCartao} | Consulta o saldo do cartão |

## Transação
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /transacoes | Executa uma transação |

## Tecnologias utilizadas

- **[Github Actions](https://github.com/features/actions)**
- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)**
- **[Hibernate](https://hibernate.org/orm/)**
- **[Lombok](https://projectlombok.org/)**
- **[Docker](https://www.docker.com/)**
- **[MySQL](https://www.mysql.com/)**
- **[JaCoCo](https://www.eclemma.org/jacoco/)**
- **[Sonar](https://www.sonarsource.com/)**
