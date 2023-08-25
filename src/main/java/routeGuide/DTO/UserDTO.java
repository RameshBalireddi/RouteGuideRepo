package routeGuide.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import routeGuide.Enum.UserRole;

@Data
public class UserDTO {

    @NotNull
    private String UserName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private UserRole role;
}
