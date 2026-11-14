package br.com.fiap.fluxo.dto.request;

import br.com.fiap.fluxo.entity.Estacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record EstacaoRequest(
        @NotBlank(message = "Informe o nome da Estação") String nome,
        @NotBlank(message = "Informe o endereço da Estação") String localizacao,
        @NotNull(message = "Informe a capacidade máxima da Estação") @Positive Integer capacidadeMaxima,
        Boolean ativa,
        @NotNull(message = "Informe as linhas que a Estação faz parte") Set<Long> linhasIds ) {

    public Estacao toEntity() {
        Estacao estacao = new Estacao();
        estacao.setNome(nome);
        estacao.setLocalizacao(localizacao);
        estacao.setCapacidadeMaxima(capacidadeMaxima);
        estacao.setEstacaoAtiva(ativa);
        return estacao;
    }
}
