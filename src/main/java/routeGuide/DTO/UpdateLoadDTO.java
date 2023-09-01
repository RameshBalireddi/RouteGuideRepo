package routeGuide.DTO;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import routeGuide.Enum.LoadStatus;

@Data
public class UpdateLoadDTO {


//   private int loadId;
    @NotNull
    @Pattern(regexp = "\\d{5,}", message = "Origin  code must have at least 5 digits")
    private String originCode;
    @NotNull
    @Pattern(regexp = "\\d{5,}", message = " destination code must have at least 5 digits")
    private String destinationCode;

    private double mileage;

    private double ratePerMile;

    private String carrierCode;

    private LoadStatus status;

}
