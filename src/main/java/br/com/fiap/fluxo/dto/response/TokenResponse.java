package br.com.fiap.fluxo.dto.response;

public record TokenResponse(
        String token,
        String tipo,
        Long expiraEm
) {
}
