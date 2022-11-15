package app;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ClienteNotFoundAdvice {

    @ResponseBody //indica que el valor retornado por el metodo es directamente escrito en la respuesta HTTP como JSON
    @ExceptionHandler(ClienteNotFoundException.class) //indica que este metodo maneja excepciones de tipo ClienteNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND) //indica que cuando se lanza una excepcion de tipo ClienteNotFoundException, se debe retornar un codigo de estado HTTP 404
    String clienteNotFoundHandler(ClienteNotFoundException ex) {
        return ex.getMessage();
    }
}
