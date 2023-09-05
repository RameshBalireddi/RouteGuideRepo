package routeGuide.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarrierAdminDto {

    @NotNull(message = "carrierName must not be null")
    private String carrierName;
    @NotNull(message = "carrier code must not be null")
    private String carrierCode;

    @Email(message = "invalid email format")
    private String contactEmail;

    @NotNull
    private String password;
}
