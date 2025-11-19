package br.com.fiap.controller;

import br.com.fiap.model.RegistroHumor;
import br.com.fiap.repository.RegistroHumorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/humores")
public class HumorController {

    @Autowired
    private RegistroHumorRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroHumor registrar(@RequestBody @Valid RegistroHumor registro) {
        registro.setDataHora(java.time.LocalDateTime.now());
        return repo.save(registro);
    }

    @GetMapping("/colaborador/{id}")
    public List<RegistroHumor> listarPorColaborador(@PathVariable Long id) {
        return repo.findByColaboradorIdAndDataHoraBetween(
                id,
                java.time.LocalDateTime.now().minusDays(7),
                java.time.LocalDateTime.now()
        );
    }
}
