package app;

class ClienteNotFoundException extends RuntimeException {
    ClienteNotFoundException(char tipoDni, Integer dni) {
        super("No se pudo encontrar el cliente con tipo de Identificacion" + tipoDni + " y numero de DNI: " + dni);
    }
}
