package br.com.fiap.fluxo.dto.request;

import br.com.fiap.fluxo.entity.Ocupacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OcupacaoRequest(
        @NotNull(message = "Informe o Id da Estação") Long estacaoId,
        @NotNull @PositiveOrZero(message = "Informe as Entradas da Estação") Integer entradas,
        @NotNull @PositiveOrZero(message = "Informe as Saídas da Estação") Integer saidas) {

    public Ocupacao toEntity() {
        Ocupacao ocupacao = new Ocupacao();
        ocupacao.setEntradas(entradas);
        ocupacao.setSaidas(saidas);
        return ocupacao;
    }
}
