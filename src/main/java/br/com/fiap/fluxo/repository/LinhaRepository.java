package br.com.fiap.fluxo.repository;

import br.com.fiap.fluxo.entity.Linha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LinhaRepository extends JpaRepository<Linha, Long>, JpaSpecificationExecutor<Linha> {
}
