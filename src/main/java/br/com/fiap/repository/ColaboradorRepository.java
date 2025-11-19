package br.com.fiap.repository;

import br.com.fiap.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    Optional<Colaborador> findByEmailCorpAndSenha(String emailCorp, String senha);

    Optional<Colaborador> findByEmailCorp(String emailCorp);

    boolean existsByEmailCorp(String emailCorp);

}