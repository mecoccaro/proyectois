package ucab.ingsw.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ProyectoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }
}
