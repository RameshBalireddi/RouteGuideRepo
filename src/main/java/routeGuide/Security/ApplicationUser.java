package routeGuide.Security;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import routeGuide.entities.Carrier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public  class ApplicationUser implements UserDetails {
    private int userId;



    @Getter
    private  String code;

    @Getter
    @Setter
    private String  carrierName;
    @Getter
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public void setCode(String code) {
        this.code = code;
    }

    public ApplicationUser(Carrier user) {
        this.userId = user.getId();
        this.carrierName=getUsername();
        this.email = user.getContactEmail();
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
        return email;
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

    public String getCarrierName() {
        return carrierName;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

