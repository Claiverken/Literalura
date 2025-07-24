# Literalura

## 📖 Sobre o Projeto
Literalura é uma aplicação do diálogo de livros interativos, desenvolvida em Java com Spring Boot, que funciona ataques da linha de comandos. O projeto permite aos utilizadores pesquisar livres atraves da API pública Gutendex, guardar os resultados numa base de dados PostgreSQL, e realizar diversas consultas sobre os dados armazenados, como listar livres, autores, e filtrar por idioma ou autores num determinado ano.

Este projeto foi desenvolvido como um desafio prático para consolidar conceitos em consumo de APIs, persistência de dados com Spring Data JPA, e construção de aplicações backend em Java.

## ✨ Funcionalidades
Uma aplicação de um menu interativo com como segundas opções:

Buscar livre pelo título: Pesquisa um livro na API Gutendex e guarda-o na base de dados local se ainda não estiver registrado.

Listar livres registrados: Exibe todos os livres guardados na base de dados.

Listar autores registros: Mostra uma lista de todos os autores guardados, junção com os seus anos de nascimento, falecimento e os vivos que escreveram.

Listar autores vivos em um determinado ano: Permite ao aprovador inserir um ano e exibe uma lista de autores que estão vivos nesse período.

Listar livres em um determinado idioma: Permite ao aproveitador escolher um idioma (inglês, português, espanhol ou franco) e lista todos os livros registrados nessa língua.

Sair: Encerra a aplicação.

## 🛠️ Tecnologias Utilizadas
O projeto foi construído como seguintes tecnologias e bibliotecas:

- Java 24: Versão da língua de programação utilizada.

- Spring Boot 3.5.3: Framework principal para a criação da aplicação.

- Spring Data JPA: Para a persistência de dados e comunicação com base de dados.

- PostgreSQL: Sistema de gestão de base de dados relacional.

- Maven: Ferramenta de gestão de dependências e automação de compilação.

- Jackson Databind: Para a serialização e desserialização de dados JSON da API.

- API Gutendex: API pública utilizada para uma busca de dados de livros.

# 🚀 Como Executar
Para executar este projeto localmente, siga os passos baixo.

Pré-requisitos
- Java Development Kit (JDK) - Versão 24 ou superior.

- Apache Maven - Para a gestão de dependências e compilação.

- PostgreSQL - Base de dados ativa e a correr.

1. Clonar o Repositório
```bash

clone Git https://github.com/seu-usuario/literalura.git
cd literalura
```
2. Configurar a Base de Dados
Uma aplicação requer uma base de dados PostgreSQL. Crie uma base de dados com o nome que prefere (por exemplo, literalura).

Uma configuração da ligação à base de dados é fé no ficheiro src/main/resources/application.properties. Terá de configurar como seguintes variáveis de ambiente no seu sistema ou IDE para que o Primavera como possau utilizar:

- DB_HOST: O endereço do seu servidor de base de dados (ex: localhost:5432).

- DB_USER: O seu nome de aprovador do PostgreSQL.

- DB_PASSWORD: A sua palavra-passe do PostgreSQL.

Propriedades
```
#Exemplo de configuração em application.properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}/Literalura
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```
O ddl-auto está configurado como update, o que significa que o Hibernate tentará criar ou atualizar automatizado como tabelas (Autores e Livros) quando uma aplicação para iniciada.

3. Compilar e Executar o Projeto
Pode compilar e executar a aplicação utilizando o Maven Wrapper incluído no projeto.

No Linux/macOS:

```
./mvnw spring-boot:run
```
No Windows:

```
./mvnw.cmd spring-boot:run
```
Após a execução, o menu interativo será exibido no seu terminal, e poderá vir a utilizar a aplicação.
<img width="1875" height="374" alt="Captura de tela 2025-07-24 174126" src="https://github.com/user-attachments/assets/c1ad56bd-5143-4fe6-95aa-86cdd171b89c" />
<img width="482" height="408" alt="Captura de tela 2025-07-24 174212" src="https://github.com/user-attachments/assets/b2e2a0a9-3a52-460b-b6fc-45f963f5b525" />
<img width="1793" height="669" alt="Captura de tela 2025-07-24 174233" src="https://github.com/user-attachments/assets/b39e13b6-8869-4831-baf8-b5a60ba15d55" />
<img width="1300" height="717" alt="Captura de tela 2025-07-24 174255" src="https://github.com/user-attachments/assets/e6b6276e-f6a3-4a62-a70c-0deb67011b80" />
<img width="1246" height="500" alt="Captura de tela 2025-07-24 174311" src="https://github.com/user-attachments/assets/3e7812fe-90e6-4588-9a31-bac13892d004" />
<img width="1065" height="650" alt="Captura de tela 2025-07-24 174329" src="https://github.com/user-attachments/assets/46001895-7d79-42d2-814f-c81ebd080ef6" />
# 🏗️ ️Estrutura do Projeto
O côdigo-fonte está organizado da segunda forma:

- `pt.claiver.literalura`

  - `LiteraluraApplication.java`: Classe principal que inicia a aplicação Spring Boot e implementa `CommandLineRunner` para executar o menu principal.

  - `modelo/`: Contém as entidades JPA (`Autor`, `Livro`) e os registros para desserialização dos dados da API (`DadosAutor`, `DadosLivro`, `DadosResultado`).

  - `repositório/`: Interfaces do Spring Data JPA (`AutorRepository`, `LivroRepository`) para as operações de base de dados.

  - `serviço/`: Contém como classes de serviço com uma lógica de negociação.

    - `LivroService.java`: Orquestra como operações, como buscar livres na API, verificar a existência na base de dados e guardar como informações.

    - `ConsumoApi.java`: Responsável por fazer como chamadas HTTP à API Gutendex.

    - `ConverterDados.java`: Utiliza o Jackson para conversor a resposta JSON da API em objetos Java.

  - `diretor/`:

    - `Principal.java`: Responsável por toda a interação com o aproveitador, exibindo o menu e processando como opções escolhidas.
