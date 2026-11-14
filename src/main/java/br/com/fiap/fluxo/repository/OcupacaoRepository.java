package br.com.fiap.fluxo.repository;

import br.com.fiap.fluxo.entity.Ocupacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcupacaoRepository extends JpaRepository<Ocupacao, Long> {
}
