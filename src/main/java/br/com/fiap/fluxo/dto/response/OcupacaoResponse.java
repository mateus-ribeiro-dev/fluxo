package br.com.fiap.fluxo.dto.response;

import br.com.fiap.fluxo.entity.Ocupacao;

import java.time.LocalDateTime;

public record OcupacaoResponse(
        Long id,
        Long estacaoId,
        Integer entradas,
        Integer saidas,
        Integer ocupacaoAtual,
        Double percentualOcupacao,
        LocalDateTime dataHora) {

    public static OcupacaoResponse from(Ocupacao ocupacao) {
        return new OcupacaoResponse(
                ocupacao.getId(),
                ocupacao.getEstacao().getId(),
                ocupacao.getEntradas(),
                ocupacao.getSaidas(),
                ocupacao.getOcupacaoAtual(),
                ocupacao.getPercentualOcupacao(),
                ocupacao.getDataHora()
        );
    }
}
