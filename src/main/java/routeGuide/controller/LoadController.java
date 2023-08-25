package routeGuide.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.LoadDTO;
import routeGuide.service.LoadService;

@RestController
public class LoadController {


    @Autowired
    LoadService loadService;

    @PostMapping("load/create")
    public ResponseEntity<APIResponse> addLoad(@Valid @RequestBody LoadDTO loadDTO){

        return loadService.addLoad(loadDTO);
    }


}
