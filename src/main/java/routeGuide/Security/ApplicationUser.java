package routeGuide.Security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import routeGuide.Enum.UserRole;
import routeGuide.entities.Carrier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public  class ApplicationUser implements UserDetails {
    private int userId;
    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;
    public ApplicationUser(Carrier user) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        UserRole role = user.getRole();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getEmail() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

