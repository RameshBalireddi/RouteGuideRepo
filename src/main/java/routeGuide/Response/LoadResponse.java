package routeGuide.Response;

import jakarta.validation.constraints.Size;
import routeGuide.Enum.LoadStatus;
import routeGuide.entities.Load;

public class LoadResponse {

    private int loadId;
    private String originCode;

    private String destinationCode;

    private double mileage;

    private double ratePerMile;

    private int carrierId;

    private LoadStatus status;

    public LoadResponse(Load load) {
        this.loadId = load.getId();
        this.originCode = load.getOriginCode();
        this.destinationCode = load.getDestinationCode();
        this.mileage = load.getMileage();
        this.ratePerMile = load.getRatePerMile();
        this.carrierId = load.getCarrier().getId();
        this.status = load.getStatus();
    }


}
