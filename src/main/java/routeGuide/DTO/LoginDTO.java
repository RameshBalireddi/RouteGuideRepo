package routeGuide.DTO;


import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDTO {

    @Email(message = "invalid email format")
    private String email;
    private String password;
}
