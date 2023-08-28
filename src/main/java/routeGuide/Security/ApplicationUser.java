package routeGuide.Security;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import routeGuide.Enum.UserRole;
import routeGuide.entities.Carrier;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public  class ApplicationUser implements UserDetails {
    private int userId;

    @Getter
    private  String code;
    private String userName;
    private String password;
      private List<GrantedAuthority> authorities;

    public void setCode(String code) {
        this.code = code;
    }

    public ApplicationUser(Carrier user) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.code=user.getCode();


       this.authorities = Collections.singletonList(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
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

