package br.com.fiap.fluxo.repository;

import br.com.fiap.fluxo.entity.Ocupacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OcupacaoRepository extends JpaRepository<Ocupacao, Long> {

    Optional<Ocupacao> findFirstByEstacaoIdOrderByDataHoraDescIdDesc(Long estacaoId);
}
