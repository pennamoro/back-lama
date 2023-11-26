# Lãma
### Back-end do projeto Lãma, Trabalho de Conclusão de Curso dos alunos:
- Geovanna Alberti Correia de Freitas GRR20210548
- Guilherme Penna Moro GRR20211633
  
O back-end desse projeto foi contruido em Java Spring Maven e usa MVC para tratar e utilizar os dados do banco de dados. Além disso no projeto estão os scripts de criação do banco de dados e sua população inicial, assim como os scripts em python que foram usados para a geração das regras de associação usadas no back-end para a recomendação personalizada de receitas de crochê para os usuários.

## Pre-requisitos:
- IDE Java ou similar, capaz de rodar projetos Maven, foi utilizado o [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/download/?section=windows) para esse projeto
- [Postgres](https://www.postgresql.org/download/) 7.0 ou acima instalado
- [JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) 17.0.9

## Pré-configurações:
- Foram utilizados os valores padrões de porta (`5432`) e nome de usuário(`postgres`) no Postgres
- A senha padrão do banco é `1234`
- O nome utilizado para o banco foi `lama`
- Qualquer alteração nessas informações devem ser alteradas no ```./resources/application.properties```
  
## Como rodar
- Faça o clone do projeto para a pasta desejada
- Faça a criação do Banco de Dados de nome lama, utilizando os scripts do arquivo `./database/Database.db`
- Utilize o arquivo `./database/lama.bkp` para restaurar os valores iniciais do banco
- Abra o projeto na usa IDE ou similar de preferencia
- Faça o download das dependencias do Maven
- Certifique-se de que o projeto esteja utilizando o JDK `17.0.9` e que a linguagem utilizada é a mesma do JDK
- Rode a aplicação no arquivo `./src/main/java/com/example/backlama/BackLamaAplication.java`
- Não esqueça de rodar o [front-end](https://github.com/gealberti/Lama) também!
