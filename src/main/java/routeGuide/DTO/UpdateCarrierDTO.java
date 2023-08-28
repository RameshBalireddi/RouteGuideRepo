package routeGuide.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCarrierDTO {

    private int carrierId;
    @Size(min = 5)
    @Digits(integer = 5, fraction = 0, message = "destination code must have at least 5 digits")
    private String carrierName;
    @Size(min = 5)
    @Digits(integer = 5, fraction = 0, message = "destination code must have at least 5 digits")
    private String carrierCode;
  @Email
    private String contactEmail;
}
