package routeGuide.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import routeGuide.entities.Carrier;

public class ObjectUtil {


    public static ApplicationUser getCarrier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;

            if (authentication != null && authentication.getPrincipal() instanceof ApplicationUser) {
                ApplicationUser carrier = (ApplicationUser) authentication.getPrincipal();

                return carrier;
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
