package routeGuide.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.UserDTO;
import routeGuide.service.UserService;

@RestController
public class UserController {


   @Autowired
   private UserService userService;
    @PostMapping("user/signup")
    public ResponseEntity<APIResponse> userCreate(@Valid @RequestBody UserDTO userDTO){

        return  userService.addUser(userDTO);
    }

}
