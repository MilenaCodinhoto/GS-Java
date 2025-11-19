package br.com.fiap.controller.ui;

import br.com.fiap.dto.CadastroColaboradorForm;
import br.com.fiap.dto.LoginForm;
import br.com.fiap.model.Colaborador;
import br.com.fiap.model.Empresa;
import br.com.fiap.repository.ColaboradorRepository;
import br.com.fiap.repository.EmpresaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ModelAndView doLogin(@ModelAttribute("loginForm") LoginForm form,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

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
    public ModelAndView processarCadastro(@ModelAttribute("form") CadastroColaboradorForm form,
                                          HttpSession session) {

        var mv = new ModelAndView("cadastro");
        mv.addObject("empresas", empresaRepo.findAll()); // pra recarregar o select em caso de erro

        if (form.getNome() == null || form.getNome().isBlank()
                || form.getEmail() == null || form.getEmail().isBlank()
                || form.getCargo() == null || form.getCargo().isBlank()
                || form.getSenha() == null || form.getSenha().isBlank()) {
            mv.addObject("erro", "Preencha todos os campos obrigatórios.");
            return mv;
        }

        if (!form.getSenha().equals(form.getConfirmarSenha())) {
            mv.addObject("erro", "As senhas não conferem.");
            return mv;
        }

        if (form.getEmpresaId() == null) {
            mv.addObject("erro", "Selecione a empresa em que você trabalha.");
            return mv;
        }

        if (colabRepo.existsByEmailCorp(form.getEmail())) {
            mv.addObject("erro", "Já existe um colaborador com este e-mail.");
            return mv;
        }

        Empresa empresa = empresaRepo.findById(form.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa selecionada não encontrada."));

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