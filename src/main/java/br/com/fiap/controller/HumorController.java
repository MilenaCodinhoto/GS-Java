package br.com.fiap.controller;

import br.com.fiap.model.RegistroHumor;
import br.com.fiap.repository.RegistroHumorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/humores")
public class HumorController {

    @Autowired
    private RegistroHumorRepository repo;

    @PostMapping
    public ResponseEntity<RegistroHumor> registrar(@RequestBody @Valid RegistroHumor registro) {
        registro.setDataHora(LocalDateTime.now());
        RegistroHumor salvo = repo.save(registro);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public List<RegistroHumor> listarTodos() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroHumor> buscarPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/colaborador/{id}")
    public List<RegistroHumor> listarPorColaborador(@PathVariable Long id) {
        return repo.findByColaboradorIdAndDataHoraBetween(
                id,
                LocalDateTime.now().minusDays(7),
                LocalDateTime.now()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}