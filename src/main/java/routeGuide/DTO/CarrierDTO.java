package routeGuide.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarrierDTO {

    @NotNull
    private String carrierName;
    @NotNull
    private String carrierCode;

    @NotNull
    private int carrierUserId;

    @Email
    private String contactEmail;


}
