package app;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController //indica que los datos retornados por cada metodos son directamente escritos en la respuesta HTTP como JSON y no se renderiza una vista
class ClienteController {

    private final IRepositorioCliente repositorio; //inyectado por el constructor en el controlador
    private final ClienteModelAssembler assembler;

    ClienteController(IRepositorioCliente repositorio, ClienteModelAssembler assembler){
        this.repositorio = repositorio;
        this.assembler = assembler;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/clientes") //indica que este metodo responde a las peticiones HTTP GET (todos)
    CollectionModel<EntityModel<Cliente>> all() { //CollectionModel retorna una coleccion encapsuladas de recursos clientes y enlaces, no una sola entidad,

        List<EntityModel<Cliente>> clientes = repositorio.findAll().stream() //stream() retorna un flujo de elementos
                .map(assembler::toModel) //map() aplica una funcion a cada elemento del flujo y retorna un nuevo flujo con los resultados
                .collect(Collectors.toList()); //collect() recopila los elementos del flujo en una coleccion

        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).all()).withSelfRel()); //CollectionModel.of() retorna una coleccion de recursos clientes y enlaces

        /*
        List<EntityModel<Cliente>> clientes = repositorio.findAll().stream()
                .map(cliente -> EntityModel.of(cliente,
                        linkTo(methodOn(ClienteController.class).one(cliente.getId())).withSelfRel(),
                        linkTo(methodOn(ClienteController.class).all()).withRel("clientes")))
                .collect(Collectors.toList());
        return CollectionModel.of(clientes, linkTo(methodOn(ClienteController.class).all()).withSelfRel());
        */
    }

    //end::get-aggregate-root[]

    @PostMapping("/clientes") //indica que este metodo responde a las peticiones HTTP POST
    ResponseEntity<?> newCliente(@RequestBody Cliente newCliente) {
        //return repositorio.save(newCliente);

        EntityModel<Cliente> entityModel = assembler.toModel(repositorio.save(newCliente));

        return ResponseEntity //crea un mensaje de estado HTTP 201 creado
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //created() retorna un codigo de estado HTTP 201
                .body(entityModel);
    }

    // Single item
    @GetMapping("/clientes/{td}/{dni}") //indica que este metodo responde a las peticiones HTTP GET por id (uno solo)
    EntityModel<Cliente> one(@PathVariable char td, @PathVariable Integer dni) { //El tipo de devolucion EtityModel es un contenedor que contiene un objeto y enlaces adicionales

        Cliente cliente = repositorio.findBy2(td,dni)
                .orElseThrow(() -> new ClienteNotFoundException(td,dni));

        /*
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).one(id)).withSelfRel(), //construye un enlace a este mismo metodo one() y lo marca como enlace self
                linkTo(methodOn(ClienteController.class).all()).withRel("clientes")); //construye un enlace a todos los clientes y lo marca como enlace clientes
        */
        return assembler.toModel(cliente);

    }


    @PutMapping("/clientes/{td}/{id}") //indica que este metodo responde a las peticiones HTTP PUT
    ResponseEntity<?> replaceCliente(@RequestBody Cliente newCliente, @PathVariable char td, @PathVariable Integer dni) {

        /*return repositorio.findById(id)
                .map(cliente -> {
                    cliente.setPrimerNombre(newCliente.getPrimerNombre());
                    cliente.setSegundoApellido(newCliente.getPrimerNombre());
                    cliente.setPrimerApellido(newCliente.getPrimerApellido());
                    cliente.setSegundoApellido(newCliente.getSegundoApellido());
                    cliente.setEdad(newCliente.getEdad());
                    cliente.setTelefono(newCliente.getTelefono());
                    cliente.setDireccion(newCliente.getDireccion());
                    cliente.setCiudadResidencia(newCliente.getCiudadResidencia());
                    return repositorio.save(cliente);
                })
                .orElseGet(() -> {
                    newCliente.setId(id);
                    return repositorio.save(newCliente);
                });

         */

        Cliente updatedCliente = repositorio.findBy2(td,dni)
                .map(cliente -> {
                    cliente.setPrimerNombre(newCliente.getPrimerNombre());
                    cliente.setSegundoApellido(newCliente.getPrimerNombre());
                    cliente.setPrimerApellido(newCliente.getPrimerApellido());
                    cliente.setSegundoApellido(newCliente.getSegundoApellido());
                    cliente.setEdad(newCliente.getEdad());
                    cliente.setTelefono(newCliente.getTelefono());
                    cliente.setDireccion(newCliente.getDireccion());
                    cliente.setCiudadResidencia(newCliente.getCiudadResidencia());
                    return repositorio.save(cliente); //retorna el cliente actualizado
                })
                .orElseGet(() -> {
                    newCliente.setTipoDni(td);
                    newCliente.setDni(dni);
                    return repositorio.save(newCliente);
                });
        EntityModel<Cliente> entityModel = assembler.toModel(updatedCliente);

        return ResponseEntity //crea un mensaje de estado HTTP 200 OK o 201
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/clientes/{td}/{dni}") //indica que este metodo responde a las peticiones HTTP DELETE
    ResponseEntity<?> deleteCliente(@PathVariable char td, @PathVariable Integer dni) {
        repositorio.deleteBy2(td,dni);

        return ResponseEntity.noContent().build(); //crea un mensaje de estado HTTP 204 no contenido
    }
}
