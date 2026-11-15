package br.com.fiap.fluxo.controller;

import br.com.fiap.fluxo.dto.request.EstacaoRequest;
import br.com.fiap.fluxo.dto.response.EstacaoResponse;
import br.com.fiap.fluxo.dto.response.LinhaResponse;
import br.com.fiap.fluxo.service.EstacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estacoes")
public class EstacaoController {
    private final EstacaoService service;

    public EstacaoController(EstacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstacaoResponse> cadastrar(@Valid @RequestBody EstacaoRequest estacaoRequest) {
        EstacaoResponse estacaoResponse = service.cadastrar(estacaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(estacaoResponse);
    }

    @GetMapping
    public Page<EstacaoResponse> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public EstacaoResponse consultarPorId(@PathVariable Long id) {
        return service.consultarPorId(id);
    }
}
