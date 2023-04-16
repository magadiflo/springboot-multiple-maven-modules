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