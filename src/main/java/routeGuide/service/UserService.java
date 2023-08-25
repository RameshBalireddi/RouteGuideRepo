package routeGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import routeGuide.APIResponse.APIResponse;
import routeGuide.DTO.UserDTO;
import routeGuide.entities.User;
import routeGuide.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public ResponseEntity<APIResponse> addUser(UserDTO userDTO) {

      User userMail=  userRepository.findByEmail(userDTO.getEmail());
      if(userMail!=null){
          return  APIResponse.errorBadRequest("given email is already registered enter new email");
      }
      User userName= userRepository.findByUserName(userDTO.getUserName());
        if(userName!=null){
            return  APIResponse.errorBadRequest("given userName is already registered enter new userName");
        }
        if (!isValidPassword(userDTO.getPassword())) {
            return APIResponse.errorBadRequest("Password must contain at least one number, one capital letter, and one special character.");
        }
        User user=new  User(userDTO,passwordEncoder);
        userRepository.save(user);
        return APIResponse.successCreate("user added successfully ",userDTO);
    }

      private boolean isValidPassword(String password) {
        // Regular expression to check for at least one number, one capital letter, and one special character
        String passwordRegex = "^(?=.*\\d)(?=.*[A-Z])(?=.*[!@#$%^&*]).*$";
        return password.matches(passwordRegex);
      }




}
