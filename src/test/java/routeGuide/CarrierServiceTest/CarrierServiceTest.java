package routeGuide.CarrierServiceTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.CarrierDTO;
import routeGuide.Enum.UserRole;
import routeGuide.entities.Carrier;
import routeGuide.repository.CarrierRepository;
import routeGuide.service.CarrierService;

public class CarrierServiceTest {

    @InjectMocks
    private CarrierService carrierService;

    @Mock
    private CarrierRepository carrierRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCarrier_Success() {
        // Create a CarrierDTO for a successful scenario
        CarrierDTO carrierDTO = new CarrierDTO();
        carrierDTO.setCarrierCode("uniqueCode");
        carrierDTO.setContactEmail("uniqueEmail");
        carrierDTO.setPassword("ValidPassword");
        carrierDTO.setRole(UserRole.CARRIER); // Change this according to your requirements

        // Mock the repository to return null for existing records
        when(carrierRepository.findByCode(carrierDTO.getCarrierCode())).thenReturn(null);
        when(carrierRepository.findByContactEmail(carrierDTO.getContactEmail())).thenReturn(null);

        // Call the method and assert the response
        ResponseEntity<APIResponse> response = carrierService.addCarrier(carrierDTO);

        // Assert that the response is successful
        assertEquals("carrier added successfully", response.getBody().getMessage());
    }

    @Test
    public void testAddCarrier_DuplicateCode() {
        // Create a CarrierDTO with a duplicate code
        CarrierDTO carrierDTO = new CarrierDTO();
        carrierDTO.setCarrierCode("duplicateCode");
        carrierDTO.setContactEmail("uniqueEmail");
        carrierDTO.setPassword("Bunny1@");
        carrierDTO.setRole(UserRole.CARRIER); // Change this according to your requirements

        // Mock the repository to return a non-null Carrier for the code
        when(carrierRepository.findByCode(carrierDTO.getCarrierCode())).thenReturn(new Carrier());

        // Call the method and assert the error response
        ResponseEntity<APIResponse> response = carrierService.addCarrier(carrierDTO);

        // Assert that the response contains an error message
        assertTrue(response.getBody().getMessage().contains("carrier code already registered"));
    }

}

