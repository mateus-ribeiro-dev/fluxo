package br.com.fiap.fluxo.specification;

import br.com.fiap.fluxo.entity.Estacao;
import org.springframework.data.jpa.domain.Specification;

public class EstacaoSpecification {

    public static Specification<Estacao> nome(String nome) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Estacao> localizacao(String localizacao) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("localizacao")), "%" + localizacao.toLowerCase() + "%");
    }

    public static Specification<Estacao> ativa(Boolean ativa) {
        return (root, query, builder) -> builder.equal(root.get("estacaoAtiva"), ativa);
    }

    public static Specification<Estacao> capacidadeMinima(Integer capacidadeMinima) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.<Integer>get("capacidadeMaxima"), capacidadeMinima);
    }
}