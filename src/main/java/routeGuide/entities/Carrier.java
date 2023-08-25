package routeGuide.entities;

import jakarta.persistence.*;
import lombok.Data;
import routeGuide.DTO.CarrierDTO;
import routeGuide.DTO.UpdateCarrierDTO;

@Data
@Entity
@Table(name="carriers")
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String code;

    private String contactEmail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Carrier(CarrierDTO carrierDTO, User user) {

        this.name=carrierDTO.getCarrierName();
        this.code=carrierDTO.getCarrierCode();
        this.contactEmail=carrierDTO.getContactEmail();
        this.user=user;

    }

    public Carrier(UpdateCarrierDTO updateCarrierDTO, User user1) {
        if(updateCarrierDTO.getCarrierName()==null) {
            this.name = updateCarrierDTO.getCarrierName();
        }
        if(updateCarrierDTO.getCarrierCode()==null) {
            this.code = updateCarrierDTO.getCarrierCode();
        }
        if(updateCarrierDTO.getContactEmail()==null) {
            this.contactEmail = updateCarrierDTO.getContactEmail();
        }
        if(user==null) {
            this.user = user1;
        }
    }
}
