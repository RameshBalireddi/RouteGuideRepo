package routeGuide.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.CarrierDTO;
import routeGuide.DTO.UpdateCarrierDTO;
import routeGuide.service.CarrierService;

@RestController
public class CarrierController {


    @Autowired
    private CarrierService carrierService;


    @PostMapping("carrier/create")
       public ResponseEntity<APIResponse> addCarrier(@RequestBody @Valid CarrierDTO carrierDTO){
       return  carrierService.addCarrier(carrierDTO);
    }

    @PutMapping("carrier/update")
    public  ResponseEntity<APIResponse> updateCarrierInfo(@RequestBody @Valid UpdateCarrierDTO updateCarrierDTO){
        return  carrierService.updateCarrierInfo(updateCarrierDTO);
    }



    @DeleteMapping("carrier")
    public  ResponseEntity<APIResponse> deleteCarrier(@PathVariable int carrierId){

        return  carrierService.deleteCarrier(carrierId);
    }

}
