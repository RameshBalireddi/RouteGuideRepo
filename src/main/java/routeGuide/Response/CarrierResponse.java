package routeGuide.Response;

import lombok.Data;
import routeGuide.entities.Carrier;

@Data
public class CarrierResponse {


    private String carrierName;

    private String carrierCode;

    private String contactEmail;

    public CarrierResponse(Carrier carrier) {
        this.carrierName = carrier.getUserName();
        this.carrierCode = carrier.getCode();
        this.contactEmail = carrier.getContactEmail();

    }


}
