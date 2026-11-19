package br.com.fiap.fluxo.repository;

import br.com.fiap.fluxo.entity.Ocupacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OcupacaoRepository extends JpaRepository<Ocupacao, Long> {

    Optional<Ocupacao> findFirstByEstacaoIdOrderByDataHoraDescIdDesc(Long estacaoId);
    Page<Ocupacao> findByEstacaoId(Long estacaoId, Pageable pageable);

    @Query("SELECT o FROM Ocupacao o WHERE o.estacao.id = :estacaoId AND o.dataHora BETWEEN :inicio AND :fim ORDER BY o.dataHora DESC")
    Page<Ocupacao> buscarPorPeriodo(@Param("estacaoId") Long estacaoId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, Pageable pageable);
}
