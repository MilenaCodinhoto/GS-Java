package br.com.fiap.controller.ui;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.model.Colaborador;
import br.com.fiap.model.RegistroHumor;
import br.com.fiap.repository.RegistroHumorRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/humores")
public class ViewHumorController {

    @Autowired
    private RegistroHumorRepository humorRepo;

    @GetMapping
    public ModelAndView listar() {
        var mv = new ModelAndView("humores");
        mv.addObject("humores", humorRepo.findAll());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo(HttpSession session, RedirectAttributes ra) {
        var colab = (Colaborador) session.getAttribute("colabLogado");

        if (colab == null) {
            ra.addFlashAttribute("erro", "Você precisa estar logado para registrar seu humor.");
            return new ModelAndView("redirect:/login");
        }

        var mv = new ModelAndView("form-humor");
        var registro = new RegistroHumor();
        registro.setColaborador(colab); // já amarra ao colaborador logado
        mv.addObject("registroHumor", registro);
        return mv;
    }

    @PostMapping
    public ModelAndView salvar(@Valid @ModelAttribute("registroHumor") RegistroHumor registro,
                               BindingResult bindingResult,
                               HttpSession session,
                               RedirectAttributes ra) {

        var colab = (Colaborador) session.getAttribute("colabLogado");

        if (colab == null) {
            ra.addFlashAttribute("erro", "Sua sessão expirou. Faça login novamente.");
            return new ModelAndView("redirect:/login");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("form-humor");
        }

        registro.setColaborador(colab); // garante que é o logado
        registro.setDataHora(java.time.LocalDateTime.now());
        humorRepo.save(registro);

        return new ModelAndView("redirect:/ui/humores");
    }
}