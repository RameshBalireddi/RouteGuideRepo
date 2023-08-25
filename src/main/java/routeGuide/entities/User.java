package routeGuide.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import routeGuide.DTO.UserDTO;
import routeGuide.Enum.UserRole;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;
    private String email;
    private String password; // Store hashed passwords
    @Enumerated(EnumType.STRING)
    private UserRole role;


    public User(UserDTO userDTO, PasswordEncoder passwordEncoder) {
        this.userName = userDTO.getUserName();
        this.email = userDTO.getEmail();
        this.password = passwordEncoder.encode(userDTO.getPassword());
        this.role = userDTO.getRole();
    }
}
