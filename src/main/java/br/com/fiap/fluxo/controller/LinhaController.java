package br.com.fiap.fluxo.controller;

import br.com.fiap.fluxo.dto.request.LinhaRequest;
import br.com.fiap.fluxo.dto.response.LinhaResponse;
import br.com.fiap.fluxo.service.LinhaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/linhas")
public class LinhaController {

    private final LinhaService service;

    public LinhaController(LinhaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LinhaResponse> cadastrar(@Valid @RequestBody LinhaRequest novaLinha) {
        LinhaResponse linhaResponse = service.cadastrar(novaLinha);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(linhaResponse);
    }

    @GetMapping
    public Page<LinhaResponse> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public LinhaResponse consultarPorId(@PathVariable Long id) {
        return service.consultarPorId(id);
    }

    @PutMapping(path = "/{linhaId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinhaResponse> atualizar(@PathVariable Long linhaId, @Valid @RequestBody LinhaRequest linha) {
        LinhaResponse linhaResponse = service.atualizar(linhaId, linha);
        return ResponseEntity.ok(linhaResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
