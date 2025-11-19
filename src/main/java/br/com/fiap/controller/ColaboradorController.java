package br.com.fiap.controller;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.model.Colaborador;
import br.com.fiap.repository.ColaboradorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradorRepository repo;

    @GetMapping
    public List<Colaborador> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Colaborador buscar(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Colaborador criar(@RequestBody @Valid Colaborador c) {
        return repo.save(c);
    }

    @PutMapping("/{id}")
    public Colaborador atualizar(@PathVariable Long id, @RequestBody @Valid Colaborador c) {
        Colaborador existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));

        existente.setNome(c.getNome());
        existente.setEmailCorp(c.getEmailCorp());
        existente.setCargo(c.getCargo());
        existente.setEmpresa(c.getEmpresa());

        return repo.save(existente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Colaborador não encontrado");
        }
        repo.deleteById(id);
    }
}