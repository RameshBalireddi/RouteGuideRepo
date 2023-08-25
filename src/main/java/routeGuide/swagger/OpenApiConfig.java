package routeGuide.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Ramesh",email = "ramesh@gmail.com"),
                description = "rout info and service",
                title = "Route Guide"),
        servers = { @Server( description = "Local Server", url = "http://localhost:8080")
        }
)
@SecurityRequirement(name = "basicAuth")
public class OpenApiConfig {

}
