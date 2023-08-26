package routeGuide.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.CarrierDTO;
import routeGuide.DTO.UpdateCarrierDTO;
import routeGuide.service.CarrierService;

import java.io.IOException;

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

    @GetMapping("carrier")
    public  ResponseEntity<APIResponse> getCarriers(){

        return  carrierService.getCarrier();
    }
    @PreAuthorize("hasAuthority(ADMIN)")
    @GetMapping("carriers/list")
    public  ResponseEntity<APIResponse> getAllCarriers(){

        return  carrierService.getAllCarriers();
    }


    @PostMapping("/import/carrier")
    public ResponseEntity<APIResponse> uploadCarriers(@RequestParam("file") MultipartFile file) {
        try {
            String fileType = getFileExtension(file.getOriginalFilename());

            if (fileType.equalsIgnoreCase("xlsx")) {
                return carrierService.saveCarriers(file.getInputStream(), fileType);
            } else {
                return APIResponse.errorBadRequest("Invalid file format. Only .xlsx files are allowed.");
            }
        } catch (IOException e) {
            return APIResponse.errorBadRequest("Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            return APIResponse.errorBadRequest("An error occurred during file upload: " + e.getMessage());
        }
    }


    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }




}
