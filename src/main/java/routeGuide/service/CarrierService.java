package routeGuide.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.CarrierDTO;
import routeGuide.DTO.LoginDTO;
import routeGuide.DTO.UpdateCarrierDTO;
import routeGuide.Enum.UserRole;
import routeGuide.Response.CarrierResponse;
import routeGuide.Security.ObjectUtil;
import routeGuide.entities.Carrier;
import routeGuide.repository.CarrierRepository;
import routeGuide.Security.ApplicationUser;

import java.io.IOException;
import java.io.InputStream;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarrierService {

  @Autowired
  JwtService jwtService;
    @Autowired
    CarrierRepository carrierRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<APIResponse> addCarrier(CarrierDTO carrierDTO) {

        Carrier carrierName = carrierRepository.findByUserName(carrierDTO.getCarrierName());
        if (carrierName != null) {
            return APIResponse.errorBadRequest("given name is already registered enter new name");
        }

        Carrier carrierCode = carrierRepository.findByCode(carrierDTO.getCarrierCode());
        if (carrierCode != null) {
            return APIResponse.errorBadRequest("carrier code already registered enter new code");
        }
        Carrier carrierEmail = carrierRepository.findByContactEmail(carrierDTO.getContactEmail());
        if (carrierEmail != null) {
            return APIResponse.errorBadRequest("given email is already registered enter new email");
        }



            Carrier carrier = new Carrier();
            carrier.setUserName(carrierDTO.getCarrierName());
            carrier.setCode(carrierDTO.getCarrierCode());
            carrier.setContactEmail(carrierDTO.getContactEmail());

            String encodedPassword = bCryptPasswordEncoder.encode(carrierDTO.getPassword());
            carrier.setPassword(encodedPassword);

            carrier.setRole(carrierDTO.getRole());

           carrierRepository.save(carrier);

           if(carrierDTO.getRole().equals(UserRole.ADMIN)){
               return  APIResponse.successCreate("Admin added successfully ", carrierDTO);
           }

        return APIResponse.successCreate("carrier added successfully ", carrierDTO);
    }


    public ResponseEntity<APIResponse> deleteCarrier(String code) {

        Carrier carrier = carrierRepository.findByCode(code);

        if (carrier == null) {
            return APIResponse.errorBadRequest("carrier code is not found enter valid carrier code");
        }
        if (carrier.getId() != ObjectUtil.getCarrierId()) {
            return APIResponse.errorUnauthorised(" you are not allow to delete to this carrier");
        }
        carrierRepository.delete(carrier);
        return APIResponse.success("carrier delete successfully ", carrier.getUserName());
    }

    public ResponseEntity<APIResponse> updateCarrierInfo(UpdateCarrierDTO updateCarrierDTO) {

          Carrier carrierCheck=carrierRepository.findByCode(updateCarrierDTO.getCode());
          if(carrierCheck==null )  {
              return  APIResponse.errorBadRequest("invalid user enter valid user code");
          }

          Carrier user = carrierRepository.findByCode(ObjectUtil.getCarrier().getCode());


        if (ObjectUtil.getCarrier().getCode() != updateCarrierDTO.getCarrierCode() || (!user.getRole().equals("ADMIN"))) {
            return APIResponse.errorUnauthorised("you are not allow to update this carrier info..");
        }
        Carrier carrierCode=   carrierRepository.findByCode(updateCarrierDTO.getCarrierCode());
        if(carrierCode!=null){
            return  APIResponse.errorBadRequest("given code is already registered give unique code");
        }

        Carrier carrier = new Carrier(updateCarrierDTO);
        carrierRepository.save(carrier);
        return APIResponse.successCreate("carrier added successfully ", carrier);

    }

    public ResponseEntity<APIResponse> getCarrier() {

        Carrier carrier = carrierRepository.findById(ObjectUtil.getCarrierId()).orElse(null);

        if (carrier == null) {
            return APIResponse.errorBadRequest("you don't have any carrier");
        }
        CarrierResponse carrierResponse = new CarrierResponse(carrier);
        return APIResponse.success("carriers : ", carrierResponse);
    }

    public ResponseEntity<APIResponse> getAllCarriers() {

        List<Carrier> carrierList = carrierRepository.findAll();

        if (carrierList.isEmpty()) {
            return APIResponse.errorBadRequest("currently don't have any carriers");
        }
        List<CarrierResponse> carrierResponses = carrierList.stream().map(c -> new CarrierResponse(c)).collect(Collectors.toList());
        return APIResponse.success("carriers : ", carrierResponses);
    }


    public ResponseEntity<APIResponse> saveCarriers(InputStream inputStream, String fileType) throws IOException {
        boolean firstLine = true;


            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Integer carrierId = (int) row.getCell(0).getNumericCellValue();
                String carrierName = row.getCell(1).getStringCellValue();
                String carrierCode = row.getCell(2).getStringCellValue();
                String carrierEmail = row.getCell(3).getStringCellValue();
                String carrierPassword = row.getCell(4).getStringCellValue();
                UserRole carrierRole = UserRole.valueOf(row.getCell(5).getStringCellValue());

                // Check for duplicates based on code, email, and username
                Carrier existingCarrierWithCode = carrierRepository.findByCode(carrierCode);
                Carrier existingCarrierWithEmail = carrierRepository.findByContactEmail(carrierEmail);
                Carrier existingCarrierWithUserName = carrierRepository.findByUserName(carrierName);

                if (existingCarrierWithCode != null || existingCarrierWithEmail != null || existingCarrierWithUserName != null) {

                    continue;
                }

                Carrier carrier = carrierRepository.findById(carrierId).orElse(null);
                if (carrier==null) {
                    carrier = new Carrier();
                    carrier.setId(carrierId);
                }
                carrier.setUserName(carrierName);
                carrier.setCode(carrierCode);
                carrier.setContactEmail(carrierEmail);
                carrier.setPassword(bCryptPasswordEncoder.encode(carrierPassword));
                carrier.setRole(carrierRole);

                carrierRepository.save(carrier);
            }

        return APIResponse.success("File uploaded successfully", fileType);
    }


    public void exportCarriers(HttpServletResponse response) throws IOException {
        List<Carrier> carriers = carrierRepository.findAll(); // Retrieve carrier data from the database

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Carriers");

        // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Carrier ID");
        headerRow.createCell(1).setCellValue("Carrier Name");
        headerRow.createCell(2).setCellValue("Carrier Code");
        headerRow.createCell(3).setCellValue("Carrier Email");
        headerRow.createCell(4).setCellValue("Carrier Role");

        // Populate the rows with carrier data
        int rowNum = 1;
        for (Carrier carrier : carriers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(carrier.getId());
            row.createCell(1).setCellValue(carrier.getUserName());
            row.createCell(2).setCellValue(carrier.getCode());
            row.createCell(3).setCellValue(carrier.getContactEmail());
            row.createCell(4).setCellValue(carrier.getRole().toString());
        }

        // Get the ServletOutputStream from the response
        ServletOutputStream outputStream = response.getOutputStream();

        // Write the workbook data to the output stream
        workbook.write(outputStream);

        // Close the workbook and output stream
        workbook.close();
        outputStream.close();
    }

    public ResponseEntity<APIResponse> loginCarrier(LoginDTO loginDTO) {
        Carrier carrier = carrierRepository.findByContactEmail(loginDTO.getEmail());

        if (carrier == null) {
            return APIResponse.errorBadRequest("Invalid user");
        }

        if (passwordEncoder.matches(loginDTO.getPassword(), carrier.getPassword())) {
            // Generate tokens
            Map<String, String> tokens = jwtService.generateTokens(loginDTO.getEmail());

            // Construct response
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("accessToken", tokens.get("accessToken"));
            responseData.put("refreshToken", tokens.get("refreshToken"));
            responseData.put("userName",carrier.getUserName());
            responseData.put("userRole",carrier.getRole());


            return APIResponse.successToken("Login successful", responseData);
        }

        return APIResponse.errorBadRequest("User password is wrong");
    }


       public ResponseEntity<APIResponse> getAccessToken() {
try {


    Map<String, String> tokens = jwtService.generateTokens(ObjectUtil.getCarrier().getEmail());

    Map<String, Object> responseData = new HashMap<>();
    responseData.put("accessToken", tokens.get("accessToken"));
    responseData.put("refreshToken", tokens.get("refreshToken"));

    return APIResponse.success("Login successful", responseData);
   }catch (RuntimeException e){
    return  APIResponse.errorBadRequest("token not found ");
 }
    }

  }






















