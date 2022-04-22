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
@RequestMapping(path = "/changePassword")
public class ChangePasswordController {

    @Autowired
    private VartotojasDAO vartotojasDAO;

    @Autowired
    private PasswordEncoder pe;

    @GetMapping
    public ModelAndView list(Authentication auth) {

        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        ModelAndView mv = new ModelAndView("changePassword");
        mv.addObject("vartotojas", v);
        return mv;
    }

    @PostMapping
    @Transactional
    public String changePassword(
            Authentication auth,
            @RequestParam(value = "oldPass", required = true) String oldPass,
            @RequestParam(value = "newPass", required = true) String newPass,
            @RequestParam(value = "newPassCheck", required = true) String newPassCheck
    ) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        if (pe.matches(oldPass, v.getSlaptazodis())) {
            if (newPass.equals(newPassCheck)) {
                v.setSlaptazodis(pe.encode(newPass));
                vartotojasDAO.save(v);
                return "redirect:./todo";
            }
        }
        return "redirect:./todo";
    }
}
