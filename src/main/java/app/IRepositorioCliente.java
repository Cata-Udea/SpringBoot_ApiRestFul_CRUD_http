package app;

import app.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//@SpringBootApplication //anotacion para que sea un componente de spring, le dice a Spring que ayude cuando pueda
public interface IRepositorioCliente extends JpaRepository<Cliente, Long> {

    //@Modifying
    //@Query("select  from clientes where cliente.tipoDni = ?1 and cliente.dni = ?2")
    Optional <Cliente> findBy2(char td, Integer dni);

    void deleteBy2(char td, Integer dni);
}

