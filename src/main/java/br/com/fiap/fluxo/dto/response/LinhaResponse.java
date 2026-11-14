package br.com.fiap.fluxo.dto.response;

import br.com.fiap.fluxo.entity.Linha;

public record LinhaResponse(
        Long id,
        String nome,
        int codigo,
        String cor,
        Boolean ativa ) {

    public static LinhaResponse from(Linha linha) {
        return new LinhaResponse(
                linha.getId(),
                linha.getNome(),
                linha.getCodigo(),
                linha.getCor(),
                linha.getLinhaAtiva()
        );
    }
}
