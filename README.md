# Clases

Cada rama contiene ejemplos de la clase, ejemplo, la rama clase3.

## Clase 3

Se construyo un proyecto con spring, para ello se entro a spring initializer (https://start.spring.io/)

Se selecciono tipo de proyecto: Maven, version de spring boot: 2.7.11 y java 11, además se agregaron varias dependencias:

- spring mvc (web): para crear los servicios REST
- spring data jpa: el orm de java
- mysql driver: el driver para conectarse a la base de datos mysql
- lombok: para anotaciones como @Data

Finalmente se crearon, la clase:

- Product: una entidad que representa un producto (con su nombre, marca, stock y un id o primary key)
- ProductController: el servicio REST inicial, que devuelve una lista de productos
