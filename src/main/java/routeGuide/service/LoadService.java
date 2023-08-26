package routeGuide.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LoadService {

    @Autowired
    LoadRepository loadRepository;

    @Autowired
    CarrierRepository carrierRepository;


    public ResponseEntity<APIResponse> addLoad(LoadDTO loadDTO) {
        Carrier carrier = carrierRepository.findById(loadDTO.getCarrierId()).orElse(null);
        if (carrier == null) {
            return APIResponse.errorBadRequest("please enter valid carried Id");
        }

//        if (loadDTO.getOriginCode()  || loadDTO.getDestinationCode() > 0) {
//            String originCodeStr = Long.toString(loadDTO.getOriginCode());
//            String destinationCodeStr = Long.toString(loadDTO.getDestinationCode());
//            if (originCodeStr.length() < 5 && destinationCodeStr.length() < 5) {
//                return APIResponse.errorBadRequest("Both originCode and destinationCode at least  5 digits");
//            }
//            if (originCodeStr.length() < 5) {
//                return APIResponse.errorBadRequest("originCode has less than 5 digits");
//            }
//            if (destinationCodeStr.length() < 5) {
//                return APIResponse.errorBadRequest("destinationCode has less than 5 digits");
//            }
//        }
        Load load = new Load(loadDTO, carrier);
        loadRepository.save(load);
        return APIResponse.successCreate("load added successfully ", load);
     }

    public ResponseEntity<APIResponse> updateLoad(UpdateLoadDTO updateLoadDTO) {
        Load load = loadRepository.findById(updateLoadDTO.getLoadId()).orElse(null);
        if (load == null) {
            return APIResponse.errorBadRequest("please enter valid load Id");
        }

//        if (updateLoadDTO.getOriginCode() > 0 || updateLoadDTO.getDestinationCode() > 0) {
//            String originCodeStr = Long.toString(updateLoadDTO.getOriginCode());
//            String destinationCodeStr = Long.toString(updateLoadDTO.getDestinationCode());
//            if (originCodeStr.length() < 5 && destinationCodeStr.length() < 5) {
//                return APIResponse.errorBadRequest("Both originCode and destinationCode at least  5 digits");
//            }
//            if (originCodeStr.length() < 5) {
//                return APIResponse.errorBadRequest("originCode has less than 5 digits");
//            }
//            if (destinationCodeStr.length() < 5) {
//                return APIResponse.errorBadRequest("destinationCode has less than 5 digits");
//            }
//        }

        Carrier carrier = carrierRepository.findById(updateLoadDTO.getCarrierId()).orElse(null);
        if(  carrier!=null && carrier.getId()!= ObjectUtil.getCarrierId()){
            return  APIResponse.errorBadRequest("you are not allow to update this load record");
        }

        if (carrier != null || updateLoadDTO.getCarrierId() != 0) {
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
        return APIResponse.success("Load updated successfully", updateLoadDTO);
    }

    public ResponseEntity<APIResponse> deleteLoadById(int loadId) {

        Load load = loadRepository.findById(loadId).orElse(null);

        if (load == null) {
            return APIResponse.errorBadRequest("load Id is not found enter load  Id");
        }
        if (load.getCarrier().getId() != ObjectUtil.getCarrierId()) {
            return APIResponse.errorUnauthorised(" you are not allow to delete to this carrier");
        }
        loadRepository.delete(load);
        return APIResponse.success("carrier delete successfully ", loadId);
    }
    public ResponseEntity<APIResponse> getLoads(){

        List<Load> loads=loadRepository.findByUserId(ObjectUtil.getCarrierId());

        if(loads.isEmpty()){
            return  APIResponse.errorBadRequest("you don't have any loads");
        }
        List<LoadResponse> loadResponses=loads.stream().map(l-> new LoadResponse(l)).collect(Collectors.toList());
        return  APIResponse.success("carriers : ",loadResponses);
    }


    public ResponseEntity<APIResponse> saveLoads(InputStream inputStream, String fileType) throws IOException {
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
                Integer carrierId = (int) row.getCell(5).getNumericCellValue();
                LoadStatus loadStatus = LoadStatus.valueOf(row.getCell(6).getStringCellValue());


                Carrier carrier = carrierRepository.findById(carrierId).orElse(null);
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

}