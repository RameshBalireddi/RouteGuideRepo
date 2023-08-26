package routeGuide.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCarrierDTO {

    private int carrierId;

    private String carrierName;

    private String carrierCode;

    private String contactEmail;
}
