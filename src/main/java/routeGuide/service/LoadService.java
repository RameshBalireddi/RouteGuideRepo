package routeGuide.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.LoadDTO;
import routeGuide.DTO.UpdateLoadDTO;
import routeGuide.Enum.LoadStatus;
import routeGuide.Enum.UserRole;
import routeGuide.Response.CarrierResponse;
import routeGuide.Response.LoadResponse;
import routeGuide.Security.ObjectUtil;
import routeGuide.entities.Carrier;
import routeGuide.entities.Load;
import routeGuide.repository.CarrierRepository;
import routeGuide.repository.LoadRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class LoadService {

    @Autowired
    LoadRepository loadRepository;

    @Autowired
    CarrierRepository carrierRepository;


    public ResponseEntity<APIResponse> addLoad(LoadDTO loadDTO) {
        Carrier carrier = carrierRepository.findByCode(loadDTO.getCarrierCode());
        if (carrier == null) {
            return APIResponse.errorBadRequest("carrier code invalid please enter valid carried code");
        }

        Load load = new Load(loadDTO, carrier);
        loadRepository.save(load);
        LoadResponse loadResponse=new LoadResponse(load);


        return APIResponse.successCreate("carrier added load successfully " , loadResponse);
     }

    public ResponseEntity<APIResponse> addLoadFromAdmin(LoadDTO loadDTO) {
        Carrier carrier = carrierRepository.findByCode(loadDTO.getCarrierCode());

        if (carrier == null) {
            return APIResponse.errorBadRequest("given carrier code is invalid please enter valid carried code");
        }
        if(!ObjectUtil.getCarrier().getRole().equals(UserRole.ADMIN)){
            return APIResponse.errorUnauthorised("you are not allowed to create Carrier");
        }

        Load load = new Load(loadDTO, carrier);
        loadRepository.save(load);
        LoadResponse loadResponse=new LoadResponse(load);

//       int loadId= load.getId();
//        Map<String, Object> loadResponse = new HashMap<>();
//        loadResponse.put("loadId", loadId);
//        loadResponse.put("loadDTO", loadDTO);
        return APIResponse.successCreate("admin added load successfully with ID: " , loadResponse);
    }

    public ResponseEntity<APIResponse> updateLoad(UpdateLoadDTO updateLoadDTO,int loadId) {
        Load load = loadRepository.findById(loadId).orElse(null);
        if (load == null) {
            return APIResponse.errorBadRequest("given load id is invalid so please enter valid load Id");
        }

        Carrier carrier = carrierRepository.findByCode(updateLoadDTO.getCarrierCode());
        if(carrier==null){
            return  APIResponse.errorBadRequest("given carrier code is invalid so please enter valid  carrier code ");
        }
        if(!load.getCarrier().getCode().equals(ObjectUtil.getCarrier().getCode()) && (!ObjectUtil.getCarrier().getRole().equals(UserRole.ADMIN)) ){
            System.out.println(!load.getCarrier().getCode().equals(ObjectUtil.getCarrier().getCode())  + "  "+ (!ObjectUtil.getCarrier().getRole().equals(UserRole.ADMIN)));
           System.out.println(load.getCarrier().getCode() + " "+ObjectUtil.getCarrier().getCode());
            return APIResponse.errorUnauthorised(" you are not allow to update this load record");
        }


        if (carrier != null || updateLoadDTO.getCarrierCode() != null) {
            load.setCarrier(carrier);
        }
        if (updateLoadDTO.getOriginCode() != null) {
            load.setOriginCode(updateLoadDTO.getOriginCode());
        }
        if (updateLoadDTO.getDestinationCode() != null) {
            load.setDestinationCode(updateLoadDTO.getDestinationCode());
        }
        if (updateLoadDTO.getMileage() != 0) {
            load.setMileage(updateLoadDTO.getMileage());
        }
        if (updateLoadDTO.getRatePerMile() != 0) {
            load.setRatePerMile(updateLoadDTO.getRatePerMile());
        }
        if(updateLoadDTO.getStatus()!=null){
            load.setStatus(updateLoadDTO.getStatus());
        }
         loadRepository.save(load);
        if(ObjectUtil.getCarrier().getRole().equals(UserRole.ADMIN)){
            return APIResponse.success("admin load updated successfully",updateLoadDTO);
        }
        return APIResponse.success("Load updated successfully", updateLoadDTO);
    }

    public ResponseEntity<APIResponse> deleteLoadById(int loadId) {

        Load load = loadRepository.findById(loadId).orElse(null);

        if (load == null) {
            return APIResponse.errorBadRequest("load Id is not found enter load  Id");
        }
        if (!load.getCarrier().getCode().equals( ObjectUtil.getCarrier().getCode())  && (!ObjectUtil.getCarrier().getRole().equals(UserRole.ADMIN))) {
            return APIResponse.errorUnauthorised(" you are not allow to delete to this carrier");
        }
        loadRepository.delete(load);

        if(ObjectUtil.getCarrier().getRole().equals(UserRole.ADMIN)){
            return APIResponse.success("admin deleted load successfully",loadId);
        }

        return APIResponse.success("carrier delete  load successfully ", loadId);
    }
    public ResponseEntity<APIResponse> getLoads(){

        List<Load> loads=loadRepository.findByUserId(ObjectUtil.getCarrierId());

        if(loads.isEmpty()){
            return  APIResponse.errorBadRequest("you don't have any loads");
        }
        List<LoadResponse> loadResponses=loads.stream().map(l-> new LoadResponse(l)).collect(Collectors.toList());
        return  APIResponse.success("your loads : ",loadResponses);
    }
    public ResponseEntity<APIResponse> getAllLoads(){

        List<Load> loads=loadRepository.findAll();

        if(loads.isEmpty()){
            return  APIResponse.errorBadRequest("you don't have any loads");
        }
        List<LoadResponse> loadResponses=loads.stream().map(l-> new LoadResponse(l)).collect(Collectors.toList());
        return  APIResponse.success("all loads list : ",loadResponses);
    }



    public ResponseEntity<APIResponse> importLoads(InputStream inputStream, String fileType) throws IOException {
        boolean firstLine = true;

        if (fileType.endsWith("xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Integer loadId = (int) row.getCell(0).getNumericCellValue();
                String originCode = String.valueOf((long) row.getCell(1).getNumericCellValue());
                String destinationCode = String.valueOf((long) row.getCell(2).getNumericCellValue());
                Double mileage = row.getCell(3).getNumericCellValue();
                Double ratePerMile = row.getCell(4).getNumericCellValue();
//                String carrierCode;
//                if (row.getCell(5).getCellType() == CellType.NUMERIC) {
//                    carrierCode = String.valueOf((int) row.getCell(5).getNumericCellValue());
//                } else {
//                    carrierCode = row.getCell(5).getStringCellValue();
//                }

                String carrierCode =  row.getCell(5).getStringCellValue();
                LoadStatus loadStatus = LoadStatus.valueOf(row.getCell(6).getStringCellValue());

                if (!originCode.matches("\\d{5,}") || !destinationCode.matches("\\d{5,}")) {
                                      continue;
                }
                Carrier carrier = carrierRepository.findByCode(carrierCode);
                if (carrier==null) {
                    continue;
                }

                Load load = loadRepository.findById(loadId).orElse(null);
                if (load==null) {
                    load = new Load();
                    load.setId(loadId);
                }

//                load.setId(loadId);
                load.setOriginCode(originCode);
                load.setDestinationCode(destinationCode);
                load.setMileage(mileage);
                load.setRatePerMile(ratePerMile);
                load.setCarrier(carrier);
                load.setStatus(loadStatus);
                loadRepository.save(load);
            }
        }
        return APIResponse.success("File uploaded successfully", fileType);
    }


    public void exportLoads(HttpServletResponse response) throws IOException {
        List<Load> loads = loadRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("loads");
        // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Load ID");
        headerRow.createCell(1).setCellValue("Origin Code");
        headerRow.createCell(2).setCellValue("Destination Code");
        headerRow.createCell(3).setCellValue("Mileage");
        headerRow.createCell(4).setCellValue("Rate Per Mile");
        headerRow.createCell(5).setCellValue("Carrier Id");
        headerRow.createCell(6).setCellValue("Status");

        int rowNum = 1;
        for (Load load : loads) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(load.getId());
            row.createCell(1).setCellValue(load.getOriginCode());
            row.createCell(2).setCellValue(load.getDestinationCode());
            row.createCell(3).setCellValue(load.getMileage());
            row.createCell(4).setCellValue(load.getRatePerMile());
            row.createCell(5).setCellValue(load.getCarrier().getId());
            row.createCell(6).setCellValue(load.getStatus().toString());
        }

        ServletOutputStream ops=response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }

}