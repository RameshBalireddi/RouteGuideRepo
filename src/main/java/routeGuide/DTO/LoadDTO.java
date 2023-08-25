package routeGuide.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import routeGuide.Enum.LoadStatus;

@Data
public class LoadDTO {

    @NotNull
    @Size(min = 5,message = "enter at least 5 numbers ")
    private long originCode;

    @NotNull
    @Size(min = 5,message = "enter at least 5 numbers ")
    private long destinationCode;

    @NotNull
    private double mileage;
    @NotNull
    private double ratePerMile;
    @NotNull
    private int carrierId;

    @NotNull
    private LoadStatus status;
}
