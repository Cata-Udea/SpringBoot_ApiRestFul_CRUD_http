package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //meta anotacion que extrae el escaneo de componentes, la config automatica y el soporte de propiedades
public class TrabajoFinalApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrabajoFinalApplication.class, args);
	}

}
