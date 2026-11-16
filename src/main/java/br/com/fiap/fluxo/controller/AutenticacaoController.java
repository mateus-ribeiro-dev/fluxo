package br.com.fiap.fluxo.controller;

import br.com.fiap.fluxo.dto.response.TokenResponse;
import br.com.fiap.fluxo.service.GeradorTokenJWTService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private final GeradorTokenJWTService geradorTokenJWTService;

    public AutenticacaoController(GeradorTokenJWTService geradorTokenJWTService) {
        this.geradorTokenJWTService = geradorTokenJWTService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(Authentication authentication) {

        String token = geradorTokenJWTService.gerarToken(authentication);

        return ResponseEntity.ok( new TokenResponse(token, "Bearer", 1800L));
    }
}
