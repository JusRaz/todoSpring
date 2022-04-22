package lt.bit.todo.dao;

import java.util.List;
import lt.bit.todo.data.Uzduotis;
import lt.bit.todo.data.Vartotojas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UzduotisDAO extends JpaRepository<Uzduotis, Integer>{
    
    @Query("select u from Uzduotis u where u.vartotojas = :vartotojas")
    public List<Uzduotis> byVartotojas(@Param("vartotojas") Vartotojas v);
    

}
