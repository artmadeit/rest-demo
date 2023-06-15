package pe.edu.cibertec.class3;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(//
        title = "API Mascotas", //
        description = "Contiene las API REST para hacer CRUD de Mascotas y Veterinarios", contact = @Contact(//
                email = "devteam@quekiwi.com", //
                name = "Equipo de desarrollo"//
        )), //
        servers = { //
                @Server(url = "http://localhost:8080", description = "Local"),
                @Server(url = "http://api.quekiwi.com", description = "Production"),
        })
public class OpenApiConfiguration {

}
