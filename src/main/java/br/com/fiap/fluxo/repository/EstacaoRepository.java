package br.com.fiap.fluxo.repository;

import br.com.fiap.fluxo.entity.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Long>, JpaSpecificationExecutor<Estacao> {
}
