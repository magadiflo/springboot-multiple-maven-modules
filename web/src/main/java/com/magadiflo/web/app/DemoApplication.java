package com.magadiflo.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @EnableJpaRepositories(basePackages = { "com.magadiflo.persistence.app" }), con esa anotación le decimos
 * que busque en el paquete "com.magadiflo.persistence.app" del módulo de persistencia nuestro bean IHotelRepository,
 * esto porque como estamos trabajando en múltiples módulos, cuando Spring hace el escaneo para buscar el bean de IHotelRepository,
 * no lo encontrará en los paquetes de este proyecto, así que debemos ser explícitos e indicarle dónde buscarlos.
 */

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.magadiflo"})
@EntityScan(basePackages = {"com.magadiflo"})
@ComponentScan(basePackages = {"com.magadiflo"})
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
