package lt.bit.todo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import lt.bit.todo.config.VartotojasDetails;
import lt.bit.todo.dao.MazaUzduotisDAO;
import lt.bit.todo.dao.UzduotisDAO;
import lt.bit.todo.dao.VartotojasDAO;
import lt.bit.todo.data.MazaUzduotis;
import lt.bit.todo.data.Uzduotis;
import lt.bit.todo.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/todo")
public class TodoController {

    @Autowired
    private UzduotisDAO uzduotisDAO;
    @Autowired
    private VartotojasDAO vartotojasDAO;
    @Autowired
    private MazaUzduotisDAO mazaUzduotisDAO;

    @GetMapping
    public ModelAndView list(Authentication auth) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        ModelAndView mv = new ModelAndView("todo");
        mv.addObject("vartotojas", v);
        mv.addObject("list", uzduotisDAO.byVartotojas(v));
        return mv;
    }
////////////////////////////////////////////////////////////////////////////////

    @GetMapping(path = "todoEdit")
    public ModelAndView showTodoEdit(
            Authentication auth,
            @RequestParam(value = "todoId", required = false) Integer todoId
    ) {
        ModelAndView mv = new ModelAndView("todoEdit");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        Uzduotis u = null;
        if (todoId != null) {
            u = uzduotisDAO.getById(todoId);
            mv.addObject("uzduotis", u);
            mv.addObject("vartotojas", v);
        } else {
            mv.addObject("vartotojas", v);
        }
        return mv;
    }
////////////////////////////////////////////////////////////////////////////////

    @GetMapping(path = "/todoDone")
    @Transactional
    public String todoDone(
            @RequestParam(value = "todoId") Integer todoId
    ) {
        Uzduotis u = uzduotisDAO.getById(todoId);
        u.setStatusas(100);
        return "redirect:../todo?vartotojasId=" + u.getVartotojas().getId();
    }
////////////////////////////////////////////////////////////////////////////////

    @PostMapping(path = "todoDetailAdd")
    @Transactional
    public String todoDetailAdd(
            @RequestParam(value = "todoId", required = false) Integer todoId,
            @RequestParam(value = "todoDetailId", required = false) Integer todoDetailId,
            @RequestParam(value = "pavadinimas", required = true) String pavadinimas,
            @RequestParam(value = "aprasymas", required = false) String aprasymas
    ) {
        MazaUzduotis mu = null;
        Uzduotis u = uzduotisDAO.getById(todoId);
        mu = new MazaUzduotis();
        mu.setUzduotis(u);
        mu.setPavadinimas(pavadinimas);
        mu.setAprasymas(aprasymas);
        if(todoDetailId == null){
        mazaUzduotisDAO.save(mu);
        }
        return "redirect:./todoEdit?todoId=" + mu.getUzduotis().getId();
    }

////////////////////////////////////////////////////////////////////////////////
    @GetMapping(path = "/todoDetailDone")
    @Transactional
    public String todoDetailDone(
            Authentication auth,
            @RequestParam(value = "todoDetailId") Integer todoDetailId,
            @RequestParam(value = "atlikta", required = false) String atliktaStr
    ) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        MazaUzduotis mu = mazaUzduotisDAO.getById(todoDetailId);
        if(todoDetailId == null){
           return "redirect:./todoEdit?todoId=" + mu.getUzduotis().getId(); 
        }
        if(!v.equals(mu.getUzduotis().getVartotojas())){
            return "redirect:./todoEdit?todoId=" + mu.getUzduotis().getId();
        }
        if(mu.getUzduotis().getAtlikta()!= null){
            return "redirect:./todoEdit?todoId=" + mu.getUzduotis().getId();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date atlikta = null;
        try {
            atlikta = sdf.parse(atliktaStr);
        } catch (Exception ex) {
            atlikta = null;
        }
        if (atlikta != null) {
            mu.setAtlikta(atlikta);
        }
        return "redirect:../todo";
    }
    
////////////////////////////////////////////////////////////////////////////////
    @PostMapping(path = "todoSave")
    @Transactional
    public String saveTodo(
            Authentication auth,
            @RequestParam(value = "todoId", required = false) Integer todoId,
            @RequestParam("pavadinimas") String pavadinimas,
            @RequestParam("aprasymas") String aprasymas,
            @RequestParam("ikiKada") String ikiKadaStr,
            @RequestParam(value = "statusas", required = false) Integer statusas,
            @RequestParam(value = "atlikta", required = false) String atliktaStr
    ) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();

        Uzduotis u = null;
        if (todoId != null) {
            u = uzduotisDAO.getById(todoId);
            if (u == null) {
                return "redirect:./todo";
            }
            if (!v.equals(u.getVartotojas())) {
                return "redirect:./todo";
            }
        } else {
            u = new Uzduotis();
            u.setVartotojas(v);
        }

        u.setPavadinimas(pavadinimas);
        u.setAprasymas(aprasymas);
        Date ikiKada = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ikiKada = sdf.parse(ikiKadaStr);
        } catch (Exception ex) {
            ikiKada = null;
        }
        if (ikiKada != null) {
            u.setIkiKada(ikiKada);
        }
        u.setStatusas(statusas);

        Date atlikta = null;
        try {
            atlikta = sdf.parse(atliktaStr);
        } catch (Exception ex) {
            atlikta = null;
        }
        if (atlikta != null) {
            u.setAtlikta(atlikta);
        }
        if (todoId == null) {
            uzduotisDAO.save(u);
        }
        return "redirect:../todo";
    }

    ////////////////////////////////////////////////////////////////////////////
    @PostMapping(path = "todoDetailSave")
    @Transactional
    public String todoDetailSave(
            @RequestParam(value = "todoId", required = true) Integer todoId,
            @RequestParam(value = "todoDetailId", required = false) Integer todoDetailId,
            @RequestParam(value = "pavadinimas", required = true) String pavadinimas,
            @RequestParam(value = "aprasymas", required = false) String aprasymas,
            @RequestParam(value = "atlikta", required = false) String atliktaStr
    ) {

        MazaUzduotis mu;
        if (todoDetailId != null) {
            mu = mazaUzduotisDAO.getById(todoDetailId);
        } else {
            mu = new MazaUzduotis();
        }
        Uzduotis u = null;
        if (todoId != null) {
            u = uzduotisDAO.getById(todoId);
        }
        mu.setUzduotis(u);
        mu.setPavadinimas(pavadinimas);
        mu.setAprasymas(aprasymas);
        Date atlikta = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            atlikta = sdf.parse(atliktaStr);
        } catch (Exception ex) {
            atlikta = null;
        }
        if (atlikta != null) {
            mu.setAtlikta(atlikta);
        }

        if (todoDetailId == null) {
            mazaUzduotisDAO.save(mu);
        }
        return "redirect:../todo";
//        return "redirect:./todoEdit?todoId=" + mu.getUzduotis().getVartotojas().getId();
    }

    ;
    ////////////////////////////////////////////////////////////////////////////
    @GetMapping(path = "/todoDelete")
    @Transactional
    public String todoDelete(
            @RequestParam(value = "todoId") Integer todoId
    ) {
        uzduotisDAO.deleteById(todoId);
        return "redirect:../todo";
    }

    ////////////////////////////////////////////////////////////////////////////
    @GetMapping(path = "/todoDetailDelete")
    @Transactional
    public String todoDetailDelete(
            @RequestParam(value = "todoDetailId") Integer todoDetailId
    ) {
        MazaUzduotis mu = mazaUzduotisDAO.getById(todoDetailId);
        mazaUzduotisDAO.deleteById(todoDetailId);
        return "redirect:./todoEdit?todoId=" + mu.getUzduotis().getId();
    }
}
