package lt.bit.todo.controllers;

import lt.bit.todo.config.VartotojasDetails;
import lt.bit.todo.dao.UzduotisDAO;
import lt.bit.todo.dao.VartotojasDAO;
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
@RequestMapping(path = "/admin/vartotojai")
public class VartotojaiController {

    @Autowired
    private VartotojasDAO vartotojasDAO;
    @Autowired
    private UzduotisDAO uzduotisDAO;

    @GetMapping
    public ModelAndView list(Authentication auth) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        ModelAndView mv = new ModelAndView("/admin/vartotojai");
        mv.addObject("vartotojas", v);
        mv.addObject("list", vartotojasDAO.findAll());
        return mv;
    }
    
    @PostMapping(path = "vartotojasEdit")
    @Transactional
    public String adminSave(
            Authentication auth,
            @RequestParam(value = "id") Integer id,
            @RequestParam("isAdmin") String isAdmin
    ) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas vAktyvus = vd.getVartotojas();
        Vartotojas v = vartotojasDAO.getById(id);

        if ("on".equals(isAdmin)) {
            isAdmin = "true";
        }
        Boolean Admin = Boolean.valueOf(isAdmin);
        if (id == null) {
            //  "Redagavimas: nebuvo perduotas id
            return "redirect:./vartotojai";
        }
        if (v == null) {
            //"Redagavimas: vartotojas su tokiu id " + id + " nerastas"
            return "redirect:./vartotojai";
        }
        if (v.equals(vAktyvus)) {
            //"Redagavimas: vartotjas negali pakeisti pats saves"
            return "redirect:./vartotojai";
        }
        v.setAdmin(Admin);
        return "redirect:../vartotojai";
    }

    @GetMapping(path = "vartotojasDelete")
    @Transactional
    public String delete(
            Authentication auth,
            @RequestParam("id") Integer id
    ) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas vAktyvus = vd.getVartotojas();
        Vartotojas v = vartotojasDAO.getById(id);
        if (id == null) {
            //  "Trynimas : nebuvo perduotas id"
            return "redirect:../vartotojai";
        }

        if (vAktyvus == null) {
            return "redirect:../vartotojai";
        }

        if (v.equals(vAktyvus)) {
            //  Vartotojas negali istrinti saves"
            return "redirect:../vartotojai";
        }
        vartotojasDAO.delete(v);
        return "redirect:../vartotojai";
    }
}
