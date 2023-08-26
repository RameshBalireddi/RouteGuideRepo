package routeGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.CarrierDTO;
import routeGuide.DTO.UpdateCarrierDTO;
import routeGuide.Response.CarrierResponse;
import routeGuide.Security.ObjectUtil;
import routeGuide.entities.Carrier;
import routeGuide.repository.CarrierRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrierService {



    @Autowired
    CarrierRepository carrierRepository;
    public ResponseEntity<APIResponse> addCarrier(CarrierDTO carrierDTO) {

        Carrier carrierName= carrierRepository.findByUserName(carrierDTO.getCarrierName());
        if(carrierName!=null){
            return  APIResponse.errorBadRequest("given name is already registered enter new name");
        }

       Carrier carrierCode= carrierRepository.findByCode(carrierDTO.getCarrierCode());
       if(carrierCode!=null){
           return  APIResponse.errorBadRequest("carrier code already registered enter new code");
       }
       Carrier carrierEmail=carrierRepository.findByContactEmail(carrierDTO.getContactEmail());
        if(carrierEmail!=null){
            return  APIResponse.errorBadRequest("given email is already registered enter new email");
        }
        Carrier carrier=new Carrier(carrierDTO);
         carrierRepository.save(carrier);
        return  APIResponse.successCreate("carrier added successfully ",carrier);
    }



    public ResponseEntity<APIResponse> deleteCarrier(int carrierId) {

        Carrier carrier = carrierRepository.findById(carrierId).orElse(null);

        if (carrier == null) {
            return APIResponse.errorBadRequest("carrier Id is not found enter valid carrier Id");
        }
        if (carrier.getId() != ObjectUtil.getCarrierId()) {
            return APIResponse.errorUnauthorised(" you are not allow to delete to this carrier");
        }
        carrierRepository.delete(carrier);
        return APIResponse.success("carrier delete successfully ", carrierId);
    }

    public ResponseEntity<APIResponse> updateCarrierInfo(UpdateCarrierDTO updateCarrierDTO) {

        Carrier user=  carrierRepository.findById(ObjectUtil.getCarrierId()).orElse(null);

        if(ObjectUtil.getCarrierId()!=updateCarrierDTO.getCarrierId()  || (!user.getRole().equals("ADMIN")) ){
            return APIResponse.errorUnauthorised("you are not allow to update this carrier info..");
        }

        Carrier carrier=new Carrier(updateCarrierDTO);
        carrierRepository.save(carrier);
        return  APIResponse.successCreate("carrier added successfully ",carrier);

    }

     public ResponseEntity<APIResponse> getCarrier(){

        Carrier carrier=carrierRepository.findById(ObjectUtil.getCarrierId()).orElse(null);

       if(carrier==null){
           return  APIResponse.errorBadRequest("you don't have any carrier");
       }
       CarrierResponse carrierResponse=new CarrierResponse(carrier);
       return  APIResponse.success("carriers : ",carrierResponse);
     }

       public ResponseEntity<APIResponse> getAllCarriers(){

        List<Carrier> carrierList=carrierRepository.findAll();

        if(carrierList.isEmpty()){
            return  APIResponse.errorBadRequest("currently don't have any carriers");
         }
        List<CarrierResponse> carrierResponses=carrierList.stream().map(c-> new CarrierResponse(c)).collect(Collectors.toList());
        return  APIResponse.success("carriers : ",carrierResponses);
     }
}
