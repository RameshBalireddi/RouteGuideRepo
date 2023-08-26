package routeGuide.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import routeGuide.DTO.LoadDTO;
import routeGuide.Enum.LoadStatus;

@Entity
@Data
@Table(name="loads")
@NoArgsConstructor
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String originCode;

    private String destinationCode;

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