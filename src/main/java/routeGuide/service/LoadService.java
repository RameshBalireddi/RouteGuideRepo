package routeGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.LoadDTO;
import routeGuide.entities.Carrier;
import routeGuide.entities.Load;
import routeGuide.repository.CarrierRepository;
import routeGuide.repository.LoadRepository;


@Service
public class LoadService {

    @Autowired
    LoadRepository loadRepository;

    @Autowired
    CarrierRepository carrierRepository;


    public ResponseEntity<APIResponse> addLoad(LoadDTO loadDTO) {

       Carrier carrier= (Carrier) carrierRepository.findById(loadDTO.getCarrierId()).orElse(null);
      if(carrier==null){
          return  APIResponse.errorBadRequest("please enter valid carried Id");
      }
        Load load =new Load(loadDTO,carrier);
        loadRepository.save(load);
        return  APIResponse.successCreate("load added successfully ",load);
    }
}
