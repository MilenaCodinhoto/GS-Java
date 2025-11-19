package br.com.fiap.repository;

import br.com.fiap.model.RegistroHumor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistroHumorRepository extends JpaRepository<RegistroHumor, Long> {

    List<RegistroHumor> findByColaboradorIdAndDataHoraBetween(
            Long colaboradorId,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    long countByColaboradorId(Long colaboradorId);

    List<RegistroHumor> findTop5ByColaboradorIdOrderByDataHoraDesc(Long colaboradorId);

    List<RegistroHumor> findByColaboradorIdOrderByDataHoraDesc(Long colaboradorId);
}
