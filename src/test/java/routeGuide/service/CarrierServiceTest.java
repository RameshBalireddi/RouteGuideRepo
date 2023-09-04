//package routeGuide.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import routeGuide.APIResponse.APIResponse;
//import routeGuide.DTO.CarrierDTO;
//import routeGuide.Enum.UserRole;
//import routeGuide.Security.ApplicationUser;
//import routeGuide.Security.ObjectUtil;
//import routeGuide.entities.Carrier;
//import routeGuide.repository.CarrierRepository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//
//class CarrierServiceTest {
//
//        @InjectMocks
//        private CarrierService carrierService;
//
//        @Mock
//        private CarrierRepository carrierRepository;
//
//        @BeforeEach
//        public void init() {
//            MockitoAnnotations.initMocks(this);
//        }
//
//        @Test
//        public void testAddCarrier_Success() {
//            // Create a CarrierDTO for a successful scenario
//            CarrierDTO carrierDTO = new CarrierDTO();
//            carrierDTO.setCarrierCode("uniqueCode");
//            carrierDTO.setContactEmail("uniqueEmail");
//            carrierDTO.setPassword("ValidPassword");
//            carrierDTO.setRole(UserRole.CARRIER); // Change this according to your requirements
//
//            // Mock the repository to return null for existing records
//            when(carrierRepository.findByCode(carrierDTO.getCarrierCode())).thenReturn(null);
//            when(carrierRepository.findByContactEmail(carrierDTO.getContactEmail())).thenReturn(null);
//
//            // Call the method and assert the response
//            ResponseEntity<APIResponse> response = carrierService.addCarrier(carrierDTO);
//
//            // Assert that the response is successful
//            assertEquals("carrier added successfully", response.getBody().getMessage());
//        }
//
//        @Test
//        public void testAddCarrier_DuplicateCode() {
//            // Create a CarrierDTO with a duplicate code
//            CarrierDTO carrierDTO = new CarrierDTO();
//            carrierDTO.setCarrierCode("duplicateCode");
//            carrierDTO.setContactEmail("uniqueEmail");
//            carrierDTO.setPassword("Bunny1@");
//            carrierDTO.setRole(UserRole.CARRIER); // Change this according to your requirements
//
//            // Mock the repository to return a non-null Carrier for the code
//            when(carrierRepository.findByCode(carrierDTO.getCarrierCode())).thenReturn(new Carrier());
//
//            // Call the method and assert the error response
//            ResponseEntity<APIResponse> response = carrierService.addCarrier(carrierDTO);
//
//            // Assert that the response contains an error message
//            assertTrue(response.getBody().getMessage().contains("carrier code already registered"));
//        }
//
//    @Test
//    public void testDeleteCarrierSuccess() {
//        // Mock data and behavior
//        String code = "carrierCode";
//        Carrier mockCarrier = new Carrier();
//        mockCarrier.setCode(code);
//        when(carrierRepository.findByCode(code)).thenReturn(mockCarrier);
//
//        // Set up a mock ObjectUtil.getCarrier() method
//        when(ObjectUtil.getCarrier()).thenReturn(new ApplicationUser());
//        when(ObjectUtil.getCarrier().getRole()).thenReturn(UserRole.CARRIER);
//
//        // Call the deleteCarrier method
//        ResponseEntity<APIResponse> response = carrierController.deleteCarrier(code);
//
//        // Assert the response
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("admin deleted carrier successfully", response.getBody().getMessage());
//        assertEquals(code, response.getBody().getData());
//    }
//}
