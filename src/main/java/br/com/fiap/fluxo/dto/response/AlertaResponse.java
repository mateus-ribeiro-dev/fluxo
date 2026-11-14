package br.com.fiap.fluxo.dto.response;

import br.com.fiap.fluxo.entity.Alerta;
import br.com.fiap.fluxo.enums.NivelAlerta;

import java.time.LocalDateTime;

public record AlertaResponse(
        Long id,
        Long estacaoId,
        Long ocupacaoId,
        String mensagem,
        NivelAlerta nivel,
        LocalDateTime dataHora) {

    public static AlertaResponse from(Alerta alerta) {
        return new AlertaResponse(
                alerta.getId(),
                alerta.getEstacao().getId(),
                alerta.getOcupacao().getId(),
                alerta.getMensagem(),
                alerta.getNivelAlerta(),
                alerta.getDataHora()
        );
    }
}
