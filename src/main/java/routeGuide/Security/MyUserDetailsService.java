package routeGuide.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import routeGuide.entities.User;
import routeGuide.repository.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUserName(userName));
        if (user == null) {
            throw new UsernameNotFoundException("user notFound");
        }
        return user.map(ApplicationUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found" + userName));
    }

}

