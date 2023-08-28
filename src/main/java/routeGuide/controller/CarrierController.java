package routeGuide.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.CarrierDTO;
import routeGuide.DTO.LoginDTO;
import routeGuide.DTO.UpdateCarrierDTO;
import routeGuide.service.CarrierService;

import java.io.IOException;
import java.rmi.server.ExportException;

@RestController
public class CarrierController {


    @Autowired
    private CarrierService carrierService;


    @PostMapping("/carrier/signup")
       public ResponseEntity<APIResponse> addCarrier(@RequestBody @Valid CarrierDTO carrierDTO){
       return  carrierService.addCarrier(carrierDTO);
    }

    @GetMapping("/carrier/login")
    public ResponseEntity<APIResponse> carrierLogin(@RequestBody LoginDTO loginDTO){
        return  carrierService.loginCarrier(loginDTO);
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("carriers/list")
    public  ResponseEntity<APIResponse> getAllCarriers() {
        try {
            return carrierService.getAllCarriers();
        } catch (AccessDeniedException e) {
            e.getMessage();
        }

     return  null;
    }


    @PostMapping("/carrier/import")
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

    @GetMapping("carrier/export")
    public void exportLoads(HttpServletResponse response) throws Exception {
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=carriers.xlsx");

            carrierService.exportCarriers(response);
        } catch (ExportException f) {
          f.getMessage();
        }
    }




}
