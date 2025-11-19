package br.com.fiap.controller.ui;

import br.com.fiap.dto.CadastroColaboradorForm;
import br.com.fiap.dto.LoginForm;
import br.com.fiap.model.Colaborador;
import br.com.fiap.model.Empresa;
import br.com.fiap.repository.ColaboradorRepository;
import br.com.fiap.repository.EmpresaRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private ColaboradorRepository colabRepo;

    @Autowired
    private EmpresaRepository empresaRepo;

    @GetMapping("/login")
    public ModelAndView loginForm() {
        var mv = new ModelAndView("login");
        mv.addObject("loginForm", new LoginForm());
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@ModelAttribute("loginForm") @Valid LoginForm form,
                                BindingResult bindingResult,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            var mv = new ModelAndView("login");
            mv.addObject("loginForm", form);
            mv.addObject("erro", "Preencha e-mail e senha corretamente.");
            return mv;
        }

        var optColab = colabRepo.findByEmailCorpAndSenha(form.getEmail(), form.getSenha());

        if (optColab.isEmpty()) {
            var mv = new ModelAndView("login");
            mv.addObject("loginForm", form);
            mv.addObject("erro", "Credenciais inválidas. Confira e tente novamente.");
            return mv;
        }

        Colaborador colaborador = optColab.get();
        session.setAttribute("colabLogado", colaborador);

        return new ModelAndView("redirect:/ui/humores");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroForm() {
        var mv = new ModelAndView("cadastro");
        mv.addObject("form", new CadastroColaboradorForm());
        mv.addObject("empresas", empresaRepo.findAll());
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView processarCadastro(@ModelAttribute("form") @Valid CadastroColaboradorForm form,
                                          BindingResult bindingResult,
                                          HttpSession session) {

        var mv = new ModelAndView("cadastro");
        mv.addObject("empresas", empresaRepo.findAll());

        if (bindingResult.hasErrors()) {
            mv.addObject("form", form);
            mv.addObject("erro", "Verifique os campos obrigatórios e tente novamente.");
            return mv;
        }

        if (!form.getSenha().equals(form.getConfirmarSenha())) {
            mv.addObject("form", form);
            mv.addObject("erro", "As senhas não conferem.");
            return mv;
        }

        if (colabRepo.existsByEmailCorp(form.getEmail())) {
            mv.addObject("form", form);
            mv.addObject("erro", "Já existe um colaborador com este e-mail.");
            return mv;
        }

        Empresa empresa = empresaRepo.findById(form.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa selecionada não encontrado."));

        Colaborador novo = new Colaborador();
        novo.setNome(form.getNome());
        novo.setEmailCorp(form.getEmail());
        novo.setCargo(form.getCargo());
        novo.setSenha(form.getSenha());
        novo.setEmpresa(empresa);

        novo = colabRepo.save(novo);

        session.setAttribute("colabLogado", novo);

        return new ModelAndView("redirect:/");
    }
}