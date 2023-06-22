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

## Clase 7

Existen varias formas de ignorar ciertos campos cuando exponga una entidad. Por ejemplo, si tenemos una entidad User:

```java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <= autoincremental
    Long id;
    String firstName;
    String email;
    String password;
}
```

Y queremos que no se muestre el password, tenemos 3 opciones:

1. Usar la anotacion @JsonIgnore por ejemplo [vea este link](https://github.com/artmadeit/rest-demo/commit/453103a02dc605b50437d8a6edc0569738795c41#r115511444)
2. [Usar un DTO](https://github.com/artmadeit/rest-demo/commit/cbdac2ecf8d31e821d46e5f51bc5cbc6cde8d17f#r115511555) y mapear la entidad al DTO [como en este link](https://github.com/artmadeit/rest-demo/commit/453103a02dc605b50437d8a6edc0569738795c41#r115511690)
3. Usar proyecciones: esto se vera en prox clases.

## Clase 8

[Un servicio REST que expone operaciones CRUD con Spring](https://github.com/artmadeit/rest-demo/commit/067616ec1ca0ff2ec937639ef746732f82d1a25c)

## Clase 9

Los query parameters son parametros pasados en una url, estas se aprecian luego de un simbolo ?.

Por ejemplo, en http://localhost:8080/mascotas?page=1, el page es el parametro y el 1 es su valor. Además si queremos pasar varios parametros la url sera como: http://localhost:8080/mascotas?page=1&size=15, aqui ve el uso del simbolo & . Este actua como un separador, de las 2 variables, page con valor 1 y size con valor 15.

Como manejamos query parameters en spring? por ejemplo digamos que tiene una api para listar veterinarios por un rango de salarios: http://localhost:8080/salarios?salarioMin=100&salarioMax=1500. El codigo para manejarlo en spring seria algo como:

```java
@GetMapping("salarios")
public List<Veterinario> findBySalarioBetween(Integer salarioMin, Integer salarioMax) {
    return veterinarioRepository.findBySalarioBetween(salarioMin, salarioMax);
}
```

El ejemplo completo puede [verse aquí](https://github.com/artmadeit/rest-demo/commit/0de0a165fcbe164149a7ad2af68cf5f8f1ce42c3)

Spring automaticamente mapea el query parameter con los argumentos de nuestro metodo. Sin embargo si queremos ser mas explicitos podemos usar la anotación @RequestParam.

```java
@GetMapping("salarios")
public List<Veterinario> findBySalarioBetween(
  @RequestParam Integer salarioMin, @RequestParam  Integer salarioMax) {
    return veterinarioRepository.findBySalarioBetween(salarioMin, salarioMax);
}
```

La anotacion @RequestParam nos puede ayudar no solo a ser mas explicitos sino a validar cierta información, por ejemplo, que si llamo a la url: http://localhost:8080/salarios?salarioMin=100, el salarioMax seria null. Para hacer requerido los parametros podemos pasarle como argumento:

```java
@GetMapping("salarios")
public List<Veterinario> findBySalarioBetween(
  @RequestParam(required=true) Integer salarioMin, @RequestParam(required=true) Integer salarioMax) {
    return veterinarioRepository.findBySalarioBetween(salarioMin, salarioMax);
}
```

Spring se encargara de dicha validacion. Ademas es util a veces que si un parametro no es requerido tenga un valor por defecto. por ejemplo si decimos http://localhost:8080/salarios?salarioMax=10000, podriamos suponer que el salario minimo es 0 y el salario maximo es 10000, en ese caso en vez de required puede usar defaultValue.

```java
@GetMapping("salarios")
public List<Veterinario> findBySalarioBetween(
  @RequestParam(defaultValue="0") Integer salarioMin, @RequestParam(required=true) Integer salarioMax) {
    return veterinarioRepository.findBySalarioBetween(salarioMin, salarioMax);
}
```

Note que el defaultValue es un string, no un entero. Esto es un requisito de spring, puesto que en si todos los query parameter son string (vea la documentación de http). Igual spring se encargara de pasar ese string a un entero (en este ejemplo, sin embargo puede usar cualquier otro tipo de datos boolean, date, etc).

## Clase 10

### Validaciones a REST

Java Bean validation, es una especificacion que nos ayuda a validar objetos, agregando anotaciones como:
@NotBlank, @Min, @Max, @Pattern (validar un patron x ejemplo, el dni o ruc), ...

Para agregar en spring boot dichas validaciones, en su archivo pom.xml agregue:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Luego anote sus clases (sea sus entidades o dtos) y para que se aplique las validaciones use @Valid en sus metodos (por ejemplo en su controlador en los metodos editar o crear). Veamos un ejemplo:

1. Anotamos nuestra clase entidad o dto segun sea el caso

```java
class Veterinario {
    @NotBlank
    String nombre;
    @NotBlank
    String apellido;
    @PositiveOrZero
    BigDecimal salario;
}
```

2. Agregamos el @Valid en nuestros metodos

```java
@RestController
class VeterinarioController {
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Veterinario register(@RequestBody @Valid Veterinario veterinario) { // <= note el @Valid
      // ...
    }

    @PutMapping("{id}")
    public Veterinario edit(Long id, @RequestBody @Valid Veterinario veterinario) { // <= note el @Valid
      // ...
    }
}
```

Y listo eso es todo 😄.

> Nota: recomendamos usar las anotaciones de java bean validation en sus entidades. Estas no solo se aplicaran cuando use el @Valid sino tambien al momento de guardarse en la BD (incluso trae muchos [otros beneficios ponerlas ahi](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate)). Además recuerde evitar el uso de DTOs lo maximo posible (como mencionamos previamente)

Si desea agregar validaciones a sus query parameters, debe hacer lo siguiente:

1. A sus @RequestParam [agregue las anotaciones de bean validation que desee.](https://github.com/artmadeit/rest-demo/commit/6946f01a0095b2a8d16baa9911bad6615af664d7)
2. A su [controller agregue el @Validated](https://github.com/artmadeit/rest-demo/commit/e509f8607e420813d464cb7980d2dc5819950dc7#r115520552)

### Proyecciones REST

Muy a menudo sus apis REST seran usadas por multiples aplicaciones (una web, una mobile, un desktop...), en el caso de aplicaciones mobiles, usted esperara recibir [menos información que una web o desktop](https://samnewman.io/patterns/architectural/bff/) (puesto que tiene menos espacio para mostrar información, las pantallas son mas chicas, y quiere reducir el consumo de datos). Un patrón muy conocido para esto son las proyecciones.

Por ejemplo digamos que tiene un metodo GET para listar veterinarios, y cada veterinario tiene estos campos:

```java
class Veterinario {
  String nombre;
  String apellido;
  LocalDate fechaNacimiento;
  String dni;
  String numeroCertificadoVeterinario;
  String especialidad;
  String descripcion;
}
```

De repente su aplicacion web muestre toda esa informacion, pero en su aplicacion movil solo necesita mostrar el nombre, apellido. En ese caso puede crear una projection en REST, indicandole un query parameter para indicar si es movil o no.

```java
    @GetMapping
    public Page<?> list(Pageable pageable, @RequestParam(defaultValue = "false") Boolean isMobile) {
      if (isMobile) {
          // en el caso de movil retorno menos campos
          return this.findResumenBy(pageable);
      } else {
          // muestro todos los campos
          return this.findAll(pageable);
      }
    }
```

El `findResumenBy` puede ser implementado de 2 formas:

1. Usando DTOs, como lo vimos en la clase 7.
   Se necesita crear un dto con los campos basicos que queremos (en este caso nombre, apellido), se llama al mismo metodo findAll() y se debe crear un mapeo del entity al dto.
   Esta opcion no es recomendada, por performance, si mi entidad tiene 20 campos, me traera dichos campos en memoria en java.
2. Usando spring data projections (recomendada)
   Si mi entidad tiene 20 campos, hara un select trayendo solo los campos que requiero: select nombre, apellido from Veterinario;

   En spring data actualmente tambien hay 2 opciones, vea [la documentacion, para mayor informacion](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections).

   Puede ver un ejemplo usando [data projections con interfaces aqui](https://github.com/artmadeit/rest-demo/commit/94c843c9c5dfd0745a09ee6bdf7ec730e254ba13)

> Nota: no confundir rest projections con spring data projections, el concepto de rest projection es usado no solo en spring sino en cualquier lenguaje o tecnologia (es usado por ejemplo en Django con python o en Laravel con php), es en si una tecnica en REST para indicar que atributos el cliente (web, mobile) necesita, de hecho hay muchas variantes y patrones mas sofisticados (vea por ejemplo Backend for frontend). Mientras que spring data projections es simplemente el termino usado para indicar que las consultas en la BD usan un select campo1, campo2 from mitabla.
> El termino projection nace de SQL, el operador SELECT tambien es conocido como projection, en el sentido de escoge solo la "informacion que necesitas". De ahi el termino es usado en REST, Event Sourcing, Spring Data,entre otros muchos lugares.

## Clase 11

El frontend puede verlo en https://github.com/artmadeit/quekiwi-frontend/tree/class11

### CORS

Cuando intente integrar su frontend a su backend, muchas veces vera un error como el siguiente:

![cors error](https://res.cloudinary.com/practicaldev/image/fetch/s--h23BwLtS--/c_imagga_scale,f_auto,fl_progressive,h_500,q_auto,w_1000/https://dev-to-uploads.s3.amazonaws.com/uploads/articles/jenik9atwnnnw815b8au.png)

Como solucionar ese error? El 1er paso es entender a que se debe ese error, si ve el error vera el termino CORS.

CORS es el acronimo de Cross-Origin Resource Sharing o en español intercambio de recursos de origen cruzado. Ya sabemos por las clases que es un Recurso (sino recuerdas es la base de REST...). Nos queda entender entonces que es origen cruzado.

Un ejemplo de solicitud de origen cruzado: cuando su javascript de su front-end servido desde https://domain-a.com hace una peticion fetch() a https://domain-b.com/data.json.

O como en el caso de aca: su frontend hace un fetch() desde http://localhost:3000 a su backend en http://localhost:8080.

Es decir los origenes son distintos. Por razones de seguridad, los navegadores restringen las peticiones HTTP de origen cruzado.

### CORS en Spring

En spring usted puede configurar su backend para seguir politicas de CORS, usando 2 formas:

1. @CrossOrigin
2. De forma global

3. Anote su metodo o controlador, por ejemplo si desea que su metodo POST use CORS, haga:

```java
@CrossOrigin(origins = "http://localhost:3000")
@PostMapping
public Veterinario registrar(Veterinario veterinario) {
  // ...
}
```

Esto hara que acepte peticiones de http://localhost:3000 (por ejemplo su frontend en React o Vuejs, si usara Angular seria algo como: http://localhost:3000).

Tambien puede ponerlo a nivel de controlador (esto hara que aplique a todos sus metodos)

```java
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class VeterinarioController {
  // ...
}
```

2. De forma global,
   si tiene muchos controladores es mejor que use esta forma. En un archivo de configuracion, puede indicar ello

```java
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
   @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // aplica a todos los controladores
                //.addMapping("/veterinarios/*") // aplica al VeterinarioController
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*"); //  * es a todos
                // .allowedMethods("POST"); solo a POST
                // .allowedMethods("POST", "GET");  a POST y GET
    }
}
```

Vea un [ejemplo completo aqui](https://github.com/artmadeit/rest-demo/commit/5840d645e595efab187fea01a87a69b1eacd241e)

## Clase 13

Existen dos enfoques para desarrollar servicios:

- API first (Top down), primero diseño la API, es decir que urls, metodos, request, query parameters,request body y responses definen mi API. Vea más en https://swagger.io/resources/articles/adopting-an-api-first-approach/
- Code first (bottom up), primero programo en mi lenguaje (java, python, php, js...) y a partir de ello puedo documentar mis APIs.

Cada uno tiene sus ventajas y desventajas.

Vea un ejemplo de [API First con Spring](https://github.com/artmadeit/rest-api-first-demo)
El ejemplo de Code First lo encontrara en este repositorio, para ello en Spring hay 2 librerias: spring fox y springdoc.

Spring fox era muy usado sin embargo esta sin mantenimiento, evite su uso!.

Para usar spring doc, instale la libreria, agregando en sus `<dependencies>` de su pom.xml, lo siguiente:

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-ui</artifactId>
  <version>1.7.0</version>
</dependency>
```

Luego cree una clase y describa los elementos principales de su API:

```java
@OpenAPIDefinition(
  info = @Info(
    title = "API Mascotas",
    description = "Contiene las API REST para hacer CRUD de Mascotas y Veterinarios",
    contact = @Contact(email = "devteam@quekiwi.com", name = "Equipo de desarrollo"
  )),
  servers = {
    @Server(url = "http://localhost:8080", description = "Local"),
    @Server(url = "http://api.quekiwi.com", description = "Production"),
  }
)
public class OpenApiConfiguration {
}
```

Para visualizar su API documentada entre a `http://localhost:8080/swagger-ui/index.html`

## Clase 15

### Más anotaciones de OpenAPI

Las anotacion @Tag es util en su controlador, esta corresponde con `tag` en open api, la cual sirve para agrupar APIs (request/response) por ejemplo todo lo relacionado a personas: crear, editar, eliminar, editar.

```java
@RestController
@RequestMapping("personas")
@Tag("personas")
public class PersonaController {
  // ...
}
```

Las anotacion @ParameterObject sirve cuando recibe objetos, en sus query string, por ejemplo.

```java
@Data
public class RangoSalario {
  Integer salarioMin;
  Integer salarioMax;
}

@RestController
@RequestMapping("personas")
public class PersonaController {

  // corresponde a http://localhost:8080/personas/salarios?salarioMin=1500&salarioMax=5000
  @GetMapping("/salarios")
  public List<Persona> filtrarPorRango(@ParameterObject RangoSalario rango) {
    // ...
  }

  // @ParameterObject es muy util cuando usa Pageable de spring
  // corresponde a http://localhost:8080/personas
  // corresponde a http://localhost:8080/personas?page=1&size=50
  @GetMapping
  public Page<Persona> filtrar(@ParameterObject Pageable rango) {
    // ...
  }

}
```

### Consumo de API REST entre servicios

Al igual que usted consumio un servicio (hizo un request) desde su frontend (por ejemplo, usando fetch o axios en caso de react y vue o HttpClient en Angular), un servicio puede consumir otro servicio.

Esta es la idea basica de SOA y microservices. En java, usted tiene varias opciones:

- A partir de java 9, existe la clase http client, antes era super tedioso...
- En spring puede usar RestTemplate (muy usado pero ahora deprecado), WebClient y a partir de spring 6, hay un nuevo http client.

Veamos RestTemplate, esta es una clase con la que usted puede hacer peticiones GET, POST, PUT, DELETE...
Por ejemplo:

Si existe un servicio que expone un metodo GET en http://unaapi.com/personas/{id} y retorna un response body como el siguiente

```json
{
  "id": 1,
  "nombre": "Arthur",
  "apellido": "Mauricio",
  "edad": 27,
  "genero": "M",
  "carrera": "..."
}
```

Si quiere hacer una peticion GET para dicho endpoint haria algo como:

```java
@Component // <= note usamos @Component o @Service, puesto que queremos que sea inyectable por Spring
public class PersonaGateway {
    public PersonaDto findById(Long id) {
        return new RestTemplate().getForObject("http://localhost:8080/personas/{id}", PersonaDto.class, id);
    }
}
```

Donde existe un archivo PersonaDto.java como el siguiente:

```java
@Data
public class PersonaDto {
  private Long id;
  private String nombre;
  private String apellido;

  // note si solo necesita nombre y apellido, los campos de abajo no son necesarios
  // siga la ley de Postel

  // private Integer edad;
  // private String genero;
  // private String carrera;
}
```

Vea el ejemplo completo en: https://github.com/artmadeit/rest-api-first-demo/blob/class15/src/main/java/pe/edu/cibertec/boletas/ServicioGateway.java

Para mayor informacion consulte:

- Http Client nativo de java, https://www.baeldung.com/java-9-http-client
- RestTemplate en spring, https://www.baeldung.com/rest-template
- Nuevo cliente spring, https://www.baeldung.com/spring-6-http-interface
- Web client spring, https://www.baeldung.com/spring-5-webclient
