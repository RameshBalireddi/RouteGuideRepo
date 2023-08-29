package routeGuide.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.LoadDTO;
import routeGuide.DTO.UpdateLoadDTO;
import routeGuide.service.LoadService;

import java.io.IOException;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/load")
public class LoadController {


    @Autowired
    LoadService loadService;
    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public ResponseEntity<APIResponse> addLoad( @RequestBody @Valid LoadDTO loadDTO){

        return loadService.addLoad(loadDTO);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/update")
    public ResponseEntity<APIResponse> updateLoad(@Valid @RequestBody UpdateLoadDTO updateLoadDTO){
        return loadService.updateLoad(updateLoadDTO);
    }
    @CrossOrigin(origins = "*")
   @DeleteMapping("/{loadId}")
   public ResponseEntity<APIResponse> deleteLoadById(@PathVariable int loadId){
       return loadService.deleteLoadById(loadId);
   }
    @CrossOrigin(origins = "*")
   @GetMapping("")
   public ResponseEntity<APIResponse> getLoads(){
       return loadService.getLoads();
   }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<APIResponse> getAllLoads(){
        return loadService.getAllLoads();
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/import")
    public ResponseEntity<APIResponse> uploadLoads(@RequestParam("file") MultipartFile file) {
        try {
            String fileType = getFileExtension(file.getOriginalFilename());

            if (fileType.equalsIgnoreCase("xlsx")) {
                return loadService.saveLoads(file.getInputStream(), fileType);
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
    @CrossOrigin(origins = "*")
    @GetMapping("/export")
    public void exportLoads(HttpServletResponse response) throws Exception {
        try {


            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=loads.xlsx");

            loadService.exportLoads(response);
        } catch (FileUploadException f) {
           f.getMessage();
        }
    }



}
