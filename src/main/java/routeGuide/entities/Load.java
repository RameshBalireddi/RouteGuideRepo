package routeGuide.entities;

import jakarta.persistence.*;
import lombok.Data;
import routeGuide.DTO.LoadDTO;
import routeGuide.Enum.LoadStatus;

@Entity
@Data
@Table(name="loads")
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long originCode;

    private long destinationCode;

    private double mileage;

    private double ratePerMile;

    @ManyToOne
    @JoinColumn(name = "carrierId")
    private Carrier carrier;

    private LoadStatus status;

    public Load(LoadDTO loadDTO, Carrier carrier) {

        this.originCode=loadDTO.getOriginCode();
        this.destinationCode=getDestinationCode();
        this.mileage=loadDTO.getMileage();
        this.ratePerMile=loadDTO.getRatePerMile();
        this.carrier=carrier;
        this.status=loadDTO.getStatus();

    }
}