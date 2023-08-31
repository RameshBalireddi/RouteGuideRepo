package routeGuide.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCarrierDTO {



    private String carrierName;

    private String carrierCode;

    @Email(message = "invalid email format please give valid email")
    private String contactEmail;
}
