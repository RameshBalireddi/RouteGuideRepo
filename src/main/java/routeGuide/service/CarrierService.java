package routeGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.CarrierDTO;
import routeGuide.DTO.UpdateCarrierDTO;
import routeGuide.Security.ObjectUtil;
import routeGuide.entities.Carrier;
import routeGuide.entities.User;
import routeGuide.repository.CarrierRepository;
import routeGuide.repository.UserRepository;

import java.util.Optional;

@Service
public class CarrierService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarrierRepository carrierRepository;
    public ResponseEntity<APIResponse> addCarrier(CarrierDTO carrierDTO) {

        User user=  userRepository.findById(ObjectUtil.getUserId()).orElse(null);
        Carrier carrier=new Carrier(carrierDTO,user);
         carrierRepository.save(carrier);
        return  APIResponse.successCreate("carrier added successfully ",carrier);
    }


    public ResponseEntity<APIResponse> deleteCarrier(int carrierId) {

        Carrier carrier = carrierRepository.findById(carrierId).orElse(null);

        if (carrier == null) {
            return APIResponse.errorBadRequest("carrier Id is not found enter valid carrier Id");
        }
        if (carrier.getUser().getId() != ObjectUtil.getUserId()) {
            return APIResponse.errorUnauthorised(" you are not allow to delete to this carrier");
        }
        carrierRepository.delete(carrier);
        return APIResponse.success("carrier delete successfully ", carrierId);
    }

    public ResponseEntity<APIResponse> updateCarrierInfo(UpdateCarrierDTO updateCarrierDTO) {

        User user=  userRepository.findById(ObjectUtil.getUserId()).orElse(null);

        if(ObjectUtil.getUserId()!=updateCarrierDTO.getCarrierId()  || (!user.getRole().equals("ADMIN")) ){
            return APIResponse.errorUnauthorised("you are not allow to update this carrier info..");
        }

        Carrier carrier=new Carrier(updateCarrierDTO,user);
        carrierRepository.save(carrier);
        return  APIResponse.successCreate("carrier added successfully ",carrier);

    }
}
