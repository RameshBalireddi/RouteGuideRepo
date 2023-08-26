package routeGuide.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import routeGuide.Enum.LoadStatus;

@Data
public class LoadDTO {

    @NotNull(message = " carrierId must not be null ")
    private int carrierId;


    @Pattern(regexp = "\\d{5,}", message = "Origin code must have at least 5 digits")
    private String originCode;


    @Pattern(regexp = "\\d{5,}", message = "Origin destination code must have at least 5 digits")
    private String  destinationCode;

    @NotNull(message = " mileage must not be null ")
    private double mileage;
    @NotNull(message = " ratePerMile must not be null ")
    private double ratePerMile;
    @NotNull(message = " status must not be null ")
    private LoadStatus status;
}
