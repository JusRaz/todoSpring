package lt.bit.todo.dao;

import java.util.List;
import lt.bit.todo.data.Vartotojas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VartotojasDAO extends JpaRepository<Vartotojas, Integer>{
    
    @Query("select v from Vartotojas v where v.vardas = :un")
    public List<Vartotojas> byVardas(@Param("un") String vardas);
    
  //  @Query("select u from Uzduotis u where u.vartotojas.id = :vartotojasId")
  //  public List<Vartotojas> byVartotojasId(@Param("vartotojasId") Integer id);
    
}
