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
  por lo tanto, necesitamos agregar las dependencias del módulo domain en el pom.xml de este nuevo módulo **persistence**:
  ````
  <dependencies>
      <dependency>
          <groupId>com.magadiflo.my-company-project</groupId>
          <artifactId>domain</artifactId>
          <version>1.0-SNAPSHOT</version>
      </dependency>
  </dependencies>
  ````
