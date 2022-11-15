package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargarDatos {
    private static final Logger log = LoggerFactory.getLogger(CargarDatos.class);

    @Bean //Los command line runner se ejecutan cuando la aplicacion esta lista y lanzan el metodo run
    CommandLineRunner initDatabase(IRepositorioCliente repositorio) {
        return args -> {
            log.info("Precargando " + repositorio.save(new Cliente('C',23445322,"Chad", "Carlos","Perez", "Gomez",  28, "234567890", "Calle 1 #2-3", "Bogota")));
            log.info("Precargando " + repositorio.save(new Cliente('P',1017200,"Maria", "Catalina", "Gomez", "Casas", 20, "123456789", "Calle 4 #5-6", "Medellin")));
            log.info("Precargando " + repositorio.save(new Cliente('E',12345,"Juan", "Pablo", "Gomez", "Casas", 25, "123456789", "Calle 7 #5-7", "Cali")));
            log.info("Precargando " + repositorio.save(new Cliente('C',28777,"Pedro", "Mario", "Gutierrez", "Casas", 27, "123456789", "Calle 8 #5-7", "Santander")));
        };
    }
}
