package br.com.fiap.controller.ui;

import br.com.fiap.model.Colaborador;
import br.com.fiap.model.RegistroHumor;
import br.com.fiap.repository.RegistroHumorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private RegistroHumorRepository humorRepo;

    @GetMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session, RedirectAttributes ra) {
        var colab = (Colaborador) session.getAttribute("colabLogado");

        if (colab == null) {
            ra.addFlashAttribute("erro", "Faça login para acessar seu dashboard emocional.");
            return new ModelAndView("redirect:/login");
        }

        Long colabId = colab.getId();
        LocalDateTime agora = LocalDateTime.now();

        // Últimos 30 dias
        LocalDateTime inicio30 = agora.minusDays(30);
        List<RegistroHumor> ultimos30 = humorRepo
                .findByColaboradorIdAndDataHoraBetween(colabId, inicio30, agora);

        double media30 = ultimos30.stream()
                .mapToInt(RegistroHumor::getNivelHumor)
                .average()
                .orElse(0.0);

        long positivos = ultimos30.stream()
                .filter(r -> r.getNivelHumor() >= 4)
                .count();

        double percPositivo = ultimos30.isEmpty()
                ? 0.0
                : (positivos * 100.0) / ultimos30.size();

        long totalRegistros = humorRepo.countByColaboradorId(colabId);

        // Últimos 5 registros para a coluna da direita
        List<RegistroHumor> ultimos5 = humorRepo
                .findTop5ByColaboradorIdOrderByDataHoraDesc(colabId);

        // Tendência dos últimos 7 dias (média por dia)
        LocalDateTime inicio7 = agora.minusDays(6).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<RegistroHumor> ultimos7 = humorRepo
                .findByColaboradorIdAndDataHoraBetween(colabId, inicio7, agora);

        Map<String, Double> mediaPorDia = ultimos7.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDataHora().toLocalDate().toString(),
                        Collectors.averagingInt(RegistroHumor::getNivelHumor)
                ));

        // ordenar por data (string yyyy-MM-dd funciona bem)
        var tendenciaOrdenada = mediaPorDia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .toList();

        var mv = new ModelAndView("dashboard");
        mv.addObject("media30", media30);
        mv.addObject("percPositivo", percPositivo);
        mv.addObject("totalRegistros", totalRegistros);
        mv.addObject("ultimosRegistros", ultimos5);
        mv.addObject("tendencia7dias", tendenciaOrdenada);
        mv.addObject("colab", colab);
        return mv;
    }
}