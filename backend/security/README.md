
# PayStack - Security (BACK-END)

Aqui é a base inicial do projeto para subir o sistema, vou passar as configurações básicas para ser utilizado (devido ao desenvolvimento, não foi gerado nenhum build do projeto).






## Variáveis de Ambiente

Para rodar esse projeto, você vai precisar alterar algumas questões do arquivo application.properties localizado na pasta de Resource do projeto.

O sistema utiliza a seguintes versões, versões diferentes podem resultar em erros na hora de subir o projeto.

- pgAdmin 4 - 7.6
- Java 17
- Node 20.09

Após o projeto instalado na maquina com as devidas versões citadas acimas corretas, rodar o comando o: ``` mvn clean install ``` em um terminal apontado para a pasta do backend


Para o desenvolvimento continuar, será necessário algumas configurações básicas, primeiro de tudo é gerar um database chamado security, na porta 5432 do pgAdmin.


- @USERNAME -> Usuário do banco
- @PASSWORD -> Senha do banco

```############### DATABASE ###############
spring.datasource.url= jdbc:postgresql://localhost:5432/security
spring.datasource.username= @USERNAME
spring.datasource.password= @PASSWORD
```


Após o projeto rodar, vai ser criado automaticamente as tabelas devido a configurações automatica do projeto, será necessário rodar um INSERT inicial para preencher os cargos inicias.

``` 
INSERT INTO public.roles(
	role_id, name)
	VALUES (1, 'ROLE_USER');
	
INSERT INTO public.roles(
	role_id, name)
	VALUES (2, 'ROLE_MODERATOR');
	
INSERT INTO public.roles(
	role_id, name)
	VALUES (3, 'ROLE_ADMIN');
```
