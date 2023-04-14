# Clases

Cada rama contiene ejemplos de la clase, ejemplo, la rama clase3.

## Requisitos

- Tener un IDE (ejemplo eclipse) o un editor (vscode)

  > En caso de vscode, instalar la extension java language pack, revisar: https://code.visualstudio.com/docs/languages/java

- Tener java 11 instalado, pruebe que este funcionando al ejecutar `java -version` en su terminal

  > Note debe tener configurado su JAVA_HOME, revise: https://www.pchardwarepro.com/como-configurar-java_home-en-windows-10/

  > Tambien puede usar herramientas como: sdkman (https://sdkman.io/) o jabba(https://github.com/shyiko/jabba), estas facilitan la instalacion de distintas versiones de java

- Tener maven instalado
  > Si usa sdkman, puede instalarlo ejecutando `sdk install maven`
- Tener mysql instalado

## Clase 3

Se construyo un proyecto con spring, para ello se entro a spring initializer (https://start.spring.io/)

Se selecciono tipo de proyecto: Maven, version de spring boot: 2.7.11 y java 11, además se agregaron varias dependencias:

- spring mvc (web): para crear los servicios REST
- spring data jpa: el ORM (Object Relational Mapping) de java
- mysql driver: el driver para conectarse a la base de datos mysql
- lombok: para anotaciones como @Data
- spring devtools: para que al hacer cualquier cambio en el codigo se reinicie el servidor

Puede tambien entrar a este [link para obtener el proyecto de 0](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.11-SNAPSHOT&packaging=jar&jvmVersion=11&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=web,devtools,data-jpa,mysql,lombok)

Finalmente se crearon las clases:

- Product: una entidad que representa un producto (con su nombre, marca, stock y un id o primary key)
- ProductController: el servicio REST inicial, que devuelve una lista de productos
