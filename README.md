# [Create a Spring Boot Application Using Multiple Maven Modules -Intermediate Spring Boot](https://www.youtube.com/watch?v=yVzi3wuTUE4)

- Creamos Nuevo Proyecto Maven desde IntelliJ IDEA
- Eliminamos el directorio **/src** creado por defecto
- Agregamos en el **.gitignore** el **.idea**

# Configurando proyecto en Spring Initializr y explora configuraciones

- Vamos a [Spring Initializr](https://start.spring.io/) y configuramos un proyecto de Spring Boot,
  con la finalidad de copiar las configuraciones generadas en su pom.xml y pegarlas en nuestro
  pom.xml del proyecto padre. Las configuraciones necesarias son:
  ````
    Project: Maven
    Language: Java
    Spring Boot: 2.7.10
    
    Project Metadata
    ----------------
    Packaging: jar
    Java: 17
    
    Dependencies
    ------------
    Spring Web
    Spring Data JPA
    H2 Database
    ````
- Una vez configurada, le damos al botón de **Explore** para ver las configuraciones que generó.
- Copiamos algunas configuraciones y las pegamos a nuestro pom padre, como el **parent**, las dependencias
  **jpa, web, h2, el de test y el plugin spring-boot-maven-plugin**
- El tipo de packaging creado implícitamente en nuestro pom.xml padre es jar,
  lo cambiamos de forma explícita a pom:
    ````
     <packaging>pom</packaging>
    ````
- Agregamos en el properties del pom.xml padre, la propiedad java.version a la versión 17.
  Esta propiedad **(java.version)** es definida en el properties del spring-boot-starter-parent con la versión 1.8,
  nosotros la sobreescribiremos con nuestra propia versión de java 17.
- Dentro del properties hay otras propiedades que nuestro proyecto padre las creó por defecto al generar
  nuestro proyecto java maven: **maven.compiler.source, maven.compiler.target y project.build.sourceEncoding**,
  dichas propiedades las eliminaremos, ya que el **spring-boot-starter-parent** ya las tiene definidas.

# Creando el primer módulo de dominio

- Clic en el proyecto padre (springboot-multiple-maven-modules)/New/Module...
- Realizamos solo las siguientes configuraciones:
  ````
  Name: domain
  Parent: springboot-multiple-maven-module <---- corresponde a nuestro proyecto padre
  ````
- Le damos en create.

## Configurando primer módulo creado

- Abrimos el pom.xml generado y vemos que en **parent** agregó los datos del proyecto padre,
  esto está bien, ya que de esa forma estamos referenciando quién es el padre de este módulo.
- Vemos también que solo agregó el **artifactId domain**, esto está bien,
  ya que el **groupId, la version** al ser los mismos que el de su proyecto padre las usará
  y no las colocará para no redundar.
- Crea las properties con **maven.compiler.source, maven.compiler.target, project.build.sourceEncoding**,
  como recordaremos, esas propiedades ya las tenemos configuradas en el proyecto padre, para ser
  más exactos el **spring-boot-starter-parent** es quien los tiene configuradas, por lo tanto
  nosotros las **eliminaremos** para no volver a redundarlas.
- Recordar que por defecto, de forma implícita, nuestro **packaging** generado es jar y es lo que necesitamos
  para este módulo, lo podemos colocar de forma explícita entre las etiquetas **packaging,** pero no es necesario.
- Finalmente, en el pom.xml del proyecto padre se genera unas etiquetas donde se registran los módulos
  que vamos creando:
  ````
  <modules>
     <module>domain</module>
  </modules>
  ````

# Creando el segundo módulo de persistencia

- Seguimos los mismos pasos que el primer módulo creado, incluso la misma configuración.

# Configurando el segundo módulo de persistencia

- Adicionalmente, a las configuraciones realizadas como en el primer módulo, necesitamos realizar una particular para
  este segundo módulo. Recordar que como será un módulo de persistencia, requerimos usar las clases de dominio,
  por lo tanto, necesitamos agregar las dependencias del módulo domain en el pom.xml de este nuevo módulo **persistence
  **:
  ````
  <dependencies>
      <dependency>
          <groupId>com.magadiflo.my-company-project</groupId>
          <artifactId>domain</artifactId>
          <version>1.0-SNAPSHOT</version>
      </dependency>
  </dependencies>
  ````

# Creando el tercer módulo web

- Configuramos tal como lo hicimos en el primer módulo.
- Agregamos las dependencias, tanto del módulo de persitence como la del domain, ya que este módulo web usará ambas.
  Esto es similar a cómo agregamos la dependencia de domain en persistence.

# Solución al error #1

````
Description:
Parameter 0 of constructor in com.magadiflo.web.app.HotelController required a bean of type 'com.magadiflo.persistence.app.IHotelRepository' that could not be found.
````

Como estamos trabajando con múltiples módulos, debemos indicarle a Spring dónde debe escanear los beans de persistencia,
agregando la siguiente anotación en la clase principal de nuestro proyecto de Sring Boot (DemoApplication):

````
@EnableJpaRepositories(basePackages = { "com.magadiflo.persistence.app" })
````

Con esa anotación le decimos que busque en el paquete "com.magadiflo.persistence.app" del módulo de persistencia
nuestro bean IHotelRepository, esto porque como estamos trabajando en múltiples módulos, cuando Spring hace el escaneo
para buscar el bean de IHotelRepository, no lo encontrará en los paquetes de este módulo web, así que debemos ser
explícitos e indicarle dónde buscarlos.

# Solución al error #2
````
Not a managed type: class com.magadiflo.domain.app.Hotel
````
Este error es similar al error #1, debemos indicarle en la clase principal de Spring Boot que escanee las entidades
que usamos en los módulos en el package donde están dichas entidades, para eso usamos la siguiente anotación:
````
@EntityScan(basePackages = {"com.magadiflo.domain.app"})
````

# Solución al error #3
Recordar que en la clase DbSeeder del módulo de persistence, agregamos la interfaz CommandLineRunner para agregar
datos por defecto al iniciar nuestra aplicación de Spring Boot, pero cuando ejecutamos no se está poblando.
Recordar además que a dicha clase le agregamos la anotación @Component, entonces la solución es similar a los dos anteriores,
debemos decirle a Spring Boot que escanee los componentes que tengan la anotación @Component en el package que le definamos:
````
@ComponentScan(basePackages = {"com.magadiflo.persistence.app", "com.magadiflo.domain.app", "com.magadiflo.web.app"})
````
Si observamos, los @Components pueden estar dispersos en todos nuestros módulos, así que le agregamos los packetes de los
otros módulos, por si en el futuro les agregamos componentes.

# Mejorar el escaneo de paquetes para los repositorios, entidades y componentes de los módulos
Como vimos en la solución de los errores #1, #2 y #3, necesitábamos agregar los paquetes donde están los 
elementos correspondientes para que Spring Boot los escanee. Eso funciona bien ya que escribimos la ruta de los
paquetes para indicarle a Spring Boot que allí debe buscarlos. Sin embargo, ocurre un pequeño problema, qué
pasaría si tuviéramos un proyecto muy grande, dichas anotaciones se llenarían de rutas apuntando a los paquetes
donde estén los elementos requeridos. Para evitar estar escribiendo muchas rutas, es necesario que desde el 
principio empecemos agregando un prefijo para que todos los paquetes tengan un inicio en común, como en mi caso
todos los módulos sus paquetes inician con: **com.magadiflo**, de esa manera le diríamos a Spring Boot que
escanee todo lo que se encuentre en dichos paquetes, así evitamos estar colocando paquete por paquete. En 
pocas palabras quedaría de esta manera:
````
@EnableJpaRepositories(basePackages = {"com.magadiflo"})
@EntityScan(basePackages = {"com.magadiflo"})
@ComponentScan(basePackages = {"com.magadiflo"})
````
De esta manera, Spring Boot va a escanear en todos los
módulos cuyos paquetes inicien con **com.magadiflo**

# Solución error #4
Cuando tratamos de compilar mediante maven:
````
mvn clean package
````
Nos muestra el error
> Unable to find main class

Para solucionar el error, debemos pasar la siguiente dependencia y plugin del 
pom.xml del padre al pom.xml del módulo web:
````
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
````
Esto es porque nuestro módulo web, es quien realmente contiene la aplicación
web, el controlador, etc. por eso movemos la dependencia de spring-boot-starter-web,
y también es el punto de entrada de nuestra aplicación por eso movemos el spring-boot-maven-plugin
para que se construya el proyecto correctamente.

Lo que dice la documentación de Spring Boot Maven Plugin: 
````
El complemento Spring Boot Maven proporciona compatibilidad con Spring Boot en Apache Maven. 
Le permite empaquetar archivos jar o war ejecutables, ejecutar aplicaciones Spring Boot, 
generar información de compilación e iniciar su aplicación Spring Boot antes de ejecutar 
las pruebas de integración.
````

# Compilando proyecto con maven
Abrimos la terminal posicionándonos en la raíz del proyecto: springboot-multiple-maven-modules y ejecutamos:
````
mvn clean package
````
Una vez finalizada la construcción de los paquetes, ejecutamos mediante la consola el .jar de nuestro 
módulo web
````
java -jar web\target\web-1.0-SNAPSHOT.jar
````
Accedemos por url, y vemos que nos devuelve los tres registros almacenados en la BD
````
http://localhost:8080/api/v0/hotels
````