package routeGuide.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import routeGuide.service.JwtService;

import java.io.IOException;
import java.rmi.server.ExportException;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/carrier")
public class CarrierController {


    @Autowired
    private CarrierService carrierService;
    @Autowired
    JwtService jwtService;


    @PostMapping("/signup")
       public ResponseEntity<APIResponse> addCarrier(@RequestBody @Valid CarrierDTO carrierDTO){
       return  carrierService.addCarrier(carrierDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create/admin")
    public ResponseEntity<APIResponse> addCarrierFromAdmin(@RequestBody @Valid CarrierDTO carrierDTO){
        return  carrierService.addCarrierFromAdmin(carrierDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> carrierLogin(@RequestBody  @Valid  LoginDTO loginDTO) {
        return carrierService.loginCarrier(loginDTO);
    }

    @PutMapping("/update/{carrierCode}")
    public  ResponseEntity<APIResponse> updateCarrierInfo(@RequestBody @Valid UpdateCarrierDTO updateCarrierDTO,@PathVariable String carrierCode){
        return  carrierService.updateCarrierInfo(updateCarrierDTO,carrierCode);
     }

    @PostMapping("/token")
    public ResponseEntity<APIResponse> carrierAccessToken() {
        return carrierService.getAccessToken();
    }


    @DeleteMapping("/{code}")
    public  ResponseEntity<APIResponse> deleteCarrier(@PathVariable String code){

        return  carrierService.deleteCarrier(code);
    }

    @GetMapping("")
    public ResponseEntity<APIResponse> getCarriers() {
        try {

            return carrierService.getCarrier();
        }catch (ExpiredJwtException e){
            return  APIResponse.errorBadRequest("token expired pls login again");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public  ResponseEntity<APIResponse> getAllCarriers() {
        try {
            return carrierService.getAllCarriers();
        } catch (AccessDeniedException e) {
            e.getMessage();
        }

     return  null;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list/ADMIN")
    public  ResponseEntity<APIResponse> getAllAdmins() {
        try {
            return carrierService.getAllAdmins();
        } catch (AccessDeniedException e) {
            e.getMessage();
        }

        return  null;
    }

    @PostMapping("/import")
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
            String filename=fileName.substring(lastDotIndex );
            System.out.print(filename);

            return fileName.substring(lastDotIndex+1 );
        }
        return "";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/export")
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
