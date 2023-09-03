package routeGuide.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import routeGuide.Enum.UserRole;
import routeGuide.entities.Carrier;

@Data
public class CarrierDTO {

    @NotNull(message = "carrierName must not be null")
    private String carrierName;
    @NotNull(message = "carrier code must not be null")
    private String carrierCode;

    @Email(message = "invalid email format")
    private String contactEmail;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;



}
