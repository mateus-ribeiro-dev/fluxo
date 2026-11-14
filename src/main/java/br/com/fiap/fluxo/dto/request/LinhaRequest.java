package br.com.fiap.fluxo.dto.request;

import br.com.fiap.fluxo.entity.Linha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LinhaRequest(
        @NotBlank(message = "Informe o nome da Linha") String nome,
        @NotNull(message = "Informe o codigo da Linha")  int codigo,
        @NotBlank(message = "Informe a cor da Linha")  String cor,
        Boolean ativa ) {

    public Linha toEntity() {
        Linha linha = new Linha();
        linha.setNome(nome);
        linha.setCodigo(codigo);
        linha.setCor(cor);
        linha.setLinhaAtiva(ativa);
        return linha;
    }
}
