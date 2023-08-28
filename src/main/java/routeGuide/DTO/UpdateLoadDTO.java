package routeGuide.DTO;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;
import routeGuide.Enum.LoadStatus;

@Data
public class UpdateLoadDTO {


   private int loadId;
   @Size(min = 5)
   @Digits(integer = 5, fraction = 0, message = "origin code must have at least 5 digits")
    private String originCode;
   @Size(min = 5)
   @Digits(integer = 5, fraction = 0, message = "destination code must have at least 5 digits")
    private String destinationCode;

    private double mileage;

    private double ratePerMile;

    private String carrierCode;

    private LoadStatus status;

}
