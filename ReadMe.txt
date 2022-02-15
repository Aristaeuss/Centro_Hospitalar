READ ME
PROJETO CENTRO HOSPITALAR UPSKILL
GRUPO 3 TURMA B

Bem-vindo ao Centro Hospitalar Upskill!

Para correr o projeto pela primeira vez, por favor crie um novo Schema no MySQL, com o nome "centrohospitalar".
Se a sua senha no MySQL for diferente de root, deve alterar a senha no ficheiro application.properties, na linha 3.
Após estes simples passos, basta correr a aplicação e acessar a plataforma através do link http://localhost:8080/

Durante o primeiro acesso, o tempo de carregamento da página inicial será um pouco mais longo, porque alguns elementos estão a ser criados:
3 utilizadores médicos (usernames: doctor1, doctor2, doctor3 - senha: test);
2 utilizadores utentes (usernames: patient1 (nif-987), patient2 (nif-654) - senha: test);
1 utilizador funcionário (username: employee - senha: test);
1 utilizador admin (username: admin - senha:admin);
horários de 2ªf a 6ªf, das 8h às 17h para os médicos;
vagas para o mês atual e o próximo para os 3 médicos.

Caso não pretenda que estes elementos sejam criados, basta comentar as linhas 36, 37 e 38 do LandingPageController, que se encontra no package controllers.
Para testar a criação de vagas, basta criar um novo médico e alterar os parametros da linha 23 da classe SlotGenerator no package config.
Ao criar utilizadores através do formulário do funcionário ou do admin, a palavra passe por defeito será o nif que registar, pode alterá-la através do link "esqueceu-se da senha" na página de login.

Obrigado pelo seu tempo e interesse em conhecer mais sobre o nosso projeto.


