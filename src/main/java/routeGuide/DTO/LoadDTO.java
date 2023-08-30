package routeGuide.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import routeGuide.Enum.LoadStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadDTO {

    @NotNull(message = " carrierId must not be null ")
    private String carrierCode;

    @NotNull
    @Pattern(regexp = "\\d{5,}", message = "Origin code must have at least 5 digits")
    private String originCode;

    @NotNull
    @Pattern(regexp = "\\d{5,}", message = "Origin destination code must have at least 5 digits")
    private String  destinationCode;

    @NotNull(message = " mileage must not be null ")
    private double mileage;

    @NotNull(message = " ratePerMile must not be null ")

    private double ratePerMile;
    @NotNull(message = " status must not be null ")

    private LoadStatus status;


}
