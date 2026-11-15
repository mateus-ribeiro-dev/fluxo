package br.com.fiap.fluxo.controller;

import br.com.fiap.fluxo.dto.response.AlertaResponse;
import br.com.fiap.fluxo.service.AlertaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<AlertaResponse> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public AlertaResponse consultarPorId(@PathVariable Long id) {
        return service.consultarPorId(id);
    }
}
