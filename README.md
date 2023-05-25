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

## Clase 3: Crear proyecto Spring para REST

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

## Clase 4: Estructura de un proyecto

Existen distintas formas de estructurar las carpetas de un proyecto

### En capas

Usualmente una aplicación puede ser [dividida 3 capas](https://martinfowler.com/bliki/PresentationDomainDataLayering.html):

- presentacion: usualmente la parte visual (sea web, mobile, ...) y cualquier salida, como exponer la aplicacion como un servicio web,
- dominio: donde se encuentra la logica del negocio (aqui van las clases del modelo del dominio, tambien conocidas como business objects o entidades)
- data: donde se encuentra la fuente de datos, sea una BD, archivos, otros servicios web usados, etc.

Puede ver tambien las capas como una funcion logica: entrada => proceso => salida, la entrada es la data, el proceso el dominio y la presentacion la salida

En el pasado, la capa de presentacion estaba alojada en el mismo proyecto. Por ejemplo en proyectos java, habia una carpeta de views donde se alojaban archivos jsp o jsf (en caso de un proyecto web) o archivos con clases swing o java fx (en caso de un proyecto desktop).

Sin embargo, en proyectos modernos, usualmente la capa de presentación esta dividida en 2, una que expone servicios web y otra que consume esos servicios web y los muestra en una interfaz visual. Es decir algo como:

- presentacion
  - ui (user interface, interfaz de usuario)
  - web services
- dominio
- data

La UI por lo general es un proyecto separado, por ejemplo, se podria realizar en Angular, Vue, React (en caso de un proyecto web), Electron (en caso de ser desktop), React Native, Flutter, Android (en caso de ser mobile).

Al separar la presentacion en 2, los servicios web pueden ser consumidos por otras aplicaciones, ser usadas por distintas UI (por ejemplo hacer que el sistema funcione web y mobile o en incluso en la TV).

Dado que la UI en si es un proyecto separado y tambien posee su estructura dependiendo de la tecnologia (framework) que se use, de aqui en adelante, nos enfocaremos en:

- web services
- dominio
- data

> NOTA: Usualmente cuando se habla de un desarrollador backend, se habla de una persona que se enfoca en estas capas, y no la de UI (a este se le conoce como desarrollador frontend), además de dividir de esta forma tiene [sus ventajas](https://martinfowler.com/bliki/PresentationDomainSeparation.html).

De igual forma que la presentacion puede ser separada, el dominio usualmente es separado en varias capas

- web services
- dominio:
  - servicios: definen las funcionalidades principales (o casos de uso) de la aplicación (en un ecommerce: Registrar Pedido, Exigir Devolucion; en un sistema de matricula: Progamar horario, Matricularse a curso; Dar de baja a un Alumno).
  - modelos (o entidades): son las clases que definen los conceptos claves de una aplicacion (en un ecommerce: Producto, Pedido, Cliente; en un sistema de matricula: Alumno, Curso, Matricula, Profesor)
  - repositorios: definen operaciones para acceder y persistir los modelos de dominio (no estan atadas a un motor de BD)
- data:
  - repositorio impl: implementan los repositorios para un motor de BD (por ejemplo MySQL, Oracle) o usando alguna tecnologia particular (por ejemplo, usando un framework MyBatis o JPA)

Note la capa de servicios puede ser opcional, a veces ciertas funcionalidades son simples que un repositorio puede reemplazarlas, es muy comun que muchas funcionalidades de una aplicación sean solo CRUDs, este consejo es explicado en mas detalle en el libro de [Patterns of Enterprise Architecture Application](https://martinfowler.com/eaaCatalog/serviceLayer.html)

Además se pueden crear capas intermedias segun sea necesario, un ejemplo comun son los DTO (data transfer object). Usualmente puede haber DTOs entre los servicios y los servicios web. Los DTO deben ser usados con precaucion, dado que generan mas [complejidad de la necesaria](https://martinfowler.com/bliki/LocalDTO.html). Además pueden existir funciones que no dependen de la aplicación en si, pueden ser usadas en varias aplicaciones, a estas se le conocen como utilitarias o comunes, estas no son parte de la arquitectura en capas pero si son de ayuda, usualmente podra verlas agrupadas en una carpeta: util, common, shared.

> Note: las funciones utilitarias son tan genericas que pueden ser movidas a una librería.

Es decir un proyecto por lo general tendra estas capas

- web services
- dto (opcional)
- services
- modelos
- repositorios
- repositorios impl
- \*utilitarios (una carpeta no parte de la arquitectura en capas)

Mas concretamente en Spring todo esto puede ser visto en paquetes (o carpetas):

- controller <= los web services son alojados aqui, el nombre controller es porque se usa Spring MVC, la C es de controller, los controller usan @RestController lo cual permite crear servicios web REST
- dto (opcional)
- services
- entities
- repositories
- util

Exiten muchos proyectos que crean interfaces y sus implementaciones para repositorios y servicios, la estructura es algo como:

- controller
- dto (opcional)
- services
  ProductService
  - impl
    ProductServiceImpl
- entities
- repositories
  ProductRepository
  - impl
    ProductRepositoryImpl
- util

El motivo principal de ello es habilitar la sustitución, [reemplazar el ProductRepositoryImpl por otra implementación. Creemos que esto hace crea complejidad accidental](https://martinfowler.com/bliki/InterfaceImplementationPair.html), es mejor tomar el camino de cuando se necesite se creara.

### Vertical slicing

A medida que un proyecto crece, es mejor separar la aplicacion en una serie de modulos por contexto de negocio en vez de separar puramente por capas. Por ejemplo, si tenemos un ecommerce, en capas se veria asi:

- controller
  ProductController
  CategoryController
  (lo mismo para cada entidad)
- dto (opcional)
- services
  ProductService
  CategoryService
  (lo mismo para cada entidad)
- entities
  Product
  Category
  Brand
  Order
  Client
  Discount
  Coupon
  Campaign
  Delivery
  Supplier
- repositories
  ProductRepository
  CategoryRepository
  (lo mismo para cada entidad)
- util

Es muy usual ver proyectos que poseen 10, 20, 50, +100 clases en cada capa, se recomienda no ir por este lado, sino aplicar vertical slicing, es decir cortar no solo horizontalmente (como las capas dicen), sino tambien verticalmente, por contexto de negocio.

- products
  - controller
  - dto (opcional)
  - services
  - entities
    Producto
    Category
    Brand
  - repositories
  - util
- discounts
  - controller
  - dto (opcional)
  - services
  - entities
    Discount
    Coupon
    Campaign
  - repositories
  - util
- delivery
- supplier
- order
- client

Nota la forma de dividir es **muy subjetiva**, puede ser de grano más fino, por ejemplo podria haber la carpeta products podria dividirse en products, brands, categories. Esto dependendera de la decisión de los desarrolladores que forman parte del equipo. Se recomienda que a medida que crezca mucho un modulo, dividirlo. Hay muchas sugerencias en libro Domain Driven Design.

Esta forma de dividir hacer proyectos grandes mas manejables y permite que cada modulo pueda ser extraido a otro proyecto (ser un nuevo servicio web o microservicio).

## Clase 10

Bean validation, es una especificacion que nos ayuda a validar objetos, agregando anotaciones como:
@NotBlank, @Min, @Max, @Pattern (validar un patron x ejemplo, el dni o ruc), ...
