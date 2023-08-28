package routeGuide.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import routeGuide.entities.Carrier;


public class ObjectUtil {


    public static Carrier getCarrier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof ApplicationUser) {
            Carrier authenticationCarrier = (Carrier) authentication.getPrincipal();

            return authenticationCarrier;
        }
        return null;
    }
    public static int getCarrierId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof ApplicationUser) {
            int userId = ((ApplicationUser) authentication.getPrincipal()).getUserId();
            return userId;
        }
        return 0;
    }
}
