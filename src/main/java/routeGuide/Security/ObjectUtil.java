package routeGuide.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import routeGuide.entities.User;

public class ObjectUtil {


    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof ApplicationUser) {
            User authenticationUser = (User) authentication.getPrincipal();
            return authenticationUser;
        }
        return null;
    }
    public static int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof ApplicationUser) {
            int userId = ((ApplicationUser) authentication.getPrincipal()).getUserId();
            return userId;
        }
        return 0;
    }
}
