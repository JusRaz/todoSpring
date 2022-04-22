package lt.bit.todo.config;

import java.util.List;
import lt.bit.todo.dao.VartotojasDAO;
import lt.bit.todo.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private VartotojasDAO vartotojasDAO;
            
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Vartotojas> list = vartotojasDAO.byVardas(username);
        if (list.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        Vartotojas v = list.get(0);
        return new VartotojasDetails(v);
    }
    
}
