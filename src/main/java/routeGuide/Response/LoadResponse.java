package routeGuide.Response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import routeGuide.Enum.LoadStatus;
import routeGuide.entities.Load;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoadResponse {

    private int loadId;
    private String originCode;

    private String destinationCode;

    private double mileage;

    private double ratePerMile;

    private String carrierCode;

    private LoadStatus status;


    public LoadResponse(Load load) {
        this.loadId = load.getId();
        this.originCode = load.getOriginCode();
        this.destinationCode = load.getDestinationCode();
        this.mileage = load.getMileage();
        this.ratePerMile = load.getRatePerMile();
        this.carrierCode = load.getCarrier().getCode();
        this.status = load.getStatus();
    }


}
