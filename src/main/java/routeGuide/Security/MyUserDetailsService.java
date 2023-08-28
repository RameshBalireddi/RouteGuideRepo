package routeGuide.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import routeGuide.entities.Carrier;
import routeGuide.repository.CarrierRepository;


import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private CarrierRepository carrierRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Carrier> user = Optional.ofNullable(carrierRepository.findByContactEmail(email));
        if (user == null) {
            throw new UsernameNotFoundException("user notFound");
        }
        return user.map(ApplicationUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found" + email));
    }

}

