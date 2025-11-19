package br.com.fiap.controller.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(HttpSession session) {
        var mv = new ModelAndView("index"); // templates/index.html
        mv.addObject("colabLogado", session.getAttribute("colabLogado"));
        return mv;
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre"; // templates/sobre.html
    }
}