package lt.bit.todo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lt.bit.todo.data.Vartotojas;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class VartotojasDetails implements UserDetails {
    
    private final Vartotojas vartotojas;
    
    public VartotojasDetails(Vartotojas vartotojas) {
        if (vartotojas == null) {
            throw new NullPointerException("vartotojas must not be null");
        }
        this.vartotojas = vartotojas;
    }

    public Vartotojas getVartotojas() {
        return vartotojas;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList();
        if (this.vartotojas.getAdmin()) {
//            list.add(new GrantedAuthority() {
//                @Override
//                public String getAuthority() {
//                    return "Admin";
//                }
//            });
            list.add(new SimpleGrantedAuthority("Admin"));
        }
        return list;
    }

    @Override
    public String getPassword() {
        return this.vartotojas.getSlaptazodis();
    }

    @Override
    public String getUsername() {
        return this.vartotojas.getVardas();
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

}
