package br.com.fiap.fluxo.controller;

import br.com.fiap.fluxo.dto.request.OcupacaoRequest;
import br.com.fiap.fluxo.dto.response.OcupacaoResponse;
import br.com.fiap.fluxo.service.OcupacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/ocupacoes")
public class OcupacaoController {
    private final OcupacaoService service;

    public OcupacaoController(OcupacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OcupacaoResponse> cadastrar(@Valid @RequestBody OcupacaoRequest ocupacaoRequest) {
        OcupacaoResponse ocupacaoResponse = service.cadastrar(ocupacaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ocupacaoResponse);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<OcupacaoResponse>> listar(@ParameterObject Pageable pageable) {
        Page<OcupacaoResponse> ocupacoes = service.listar(pageable);
        return ResponseEntity.ok(ocupacoes);
    }

    @GetMapping("/estacao/{estacaoId}")
    public Page<OcupacaoResponse> consultarPorEstacao(@PathVariable("estacaoId") Long estacaoId,Pageable pageable) {
        return service.consultarPorEstacao(estacaoId, pageable);
    }

    @GetMapping("/periodo")
    public Page<OcupacaoResponse> consultarPorPeriodo(@RequestParam Long estacaoId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim, Pageable pageable) {

        return service.consultarPorPeriodo(estacaoId, inicio, fim, pageable);
    }
}
