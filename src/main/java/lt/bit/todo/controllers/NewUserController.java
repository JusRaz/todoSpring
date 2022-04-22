package lt.bit.todo.controllers;

import lt.bit.todo.config.VartotojasDetails;
import lt.bit.todo.dao.VartotojasDAO;
import lt.bit.todo.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/register")
public class NewUserController {

    @Autowired
    private VartotojasDAO vartotojasDAO;

    @Autowired
    private PasswordEncoder pe;

    @GetMapping
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("register");
        return mv;
    }

    @PostMapping(path = "register")
    @Transactional
    public String register(
            @RequestParam(value = "vardas", required = false) String vardas,
            @RequestParam(value = "slaptazodis", required = false) String slaptazodis,
            @RequestParam(value = "slaptazodis2", required = false) String slaptazodis2
    ) {
        if (vardas == null || vardas.trim().equals("")) {
            return "redirect:../register";
        }
        if (slaptazodis == null || slaptazodis.trim().equals("")) {
            return "redirect:../register";
        }
        if (!slaptazodis.equals(slaptazodis2)) {
            return "redirect:../register";
        }
        
        Vartotojas v = new Vartotojas();
        v.setVardas(vardas);
        v.setSlaptazodis(pe.encode(slaptazodis));
        
        return "redirect:../index.html";
    }
}
