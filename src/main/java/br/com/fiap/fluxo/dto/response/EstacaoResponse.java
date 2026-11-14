package br.com.fiap.fluxo.dto.response;

import br.com.fiap.fluxo.entity.Estacao;
import br.com.fiap.fluxo.entity.Linha;

import java.util.Set;
import java.util.stream.Collectors;

public record EstacaoResponse(
        Long id,
        String nome,
        String localizacao,
        Integer capacidadeMaxima,
        Boolean ativa,
        Set<Long> linhasIds) {

    public static EstacaoResponse from(Estacao estacao) {
        return new EstacaoResponse(
                estacao.getId(),
                estacao.getNome(),
                estacao.getLocalizacao(),
                estacao.getCapacidadeMaxima(),
                estacao.getEstacaoAtiva(),
                estacao.getLinhas()
                        .stream()
                        .map(Linha::getId)
                        .collect(Collectors.toSet())
        );
    }
}
