package routeGuide.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.LoadDTO;
import routeGuide.DTO.UpdateLoadDTO;
import routeGuide.service.LoadService;

@RestController
public class LoadController {


    @Autowired
    LoadService loadService;

    @PostMapping("load/create")
    public ResponseEntity<APIResponse> addLoad( @RequestBody @Valid LoadDTO loadDTO){

        return loadService.addLoad(loadDTO);
    }

    @PostMapping("load/update")
    public ResponseEntity<APIResponse> updateLoad(@Valid @RequestBody UpdateLoadDTO updateLoadDTO){
        return loadService.updateLoad(updateLoadDTO);
    }

   @DeleteMapping("/{loadId}")
   public ResponseEntity<APIResponse> deleteLoadById(@PathVariable int loadId){
       return loadService.deleteLoadById(loadId);
   }

   @GetMapping("loads")
   public ResponseEntity<APIResponse> getLoads(){
       return loadService.getLoads();
   }

}
