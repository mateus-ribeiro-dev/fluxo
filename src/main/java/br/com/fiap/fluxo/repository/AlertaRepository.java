package br.com.fiap.fluxo.repository;

import br.com.fiap.fluxo.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.fiap.fluxo.enums.NivelAlerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    Page<Alerta> findByNivelAlerta(NivelAlerta nivelAlerta, Pageable pageable);

    Page<Alerta> findByEstacaoId(Long estacaoId, Pageable pageable);
}
