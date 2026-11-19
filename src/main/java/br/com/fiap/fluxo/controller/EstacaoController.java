package br.com.fiap.fluxo.controller;

import br.com.fiap.fluxo.dto.request.EstacaoRequest;
import br.com.fiap.fluxo.dto.response.EstacaoResponse;
import br.com.fiap.fluxo.service.EstacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.MediaTypes;
import org.springdoc.core.annotations.ParameterObject;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<EstacaoResponse>> listar(@ParameterObject Pageable pageable) {
        Page<EstacaoResponse> estacoes = service.listar(pageable);
        return ResponseEntity.ok(estacoes);
    }

    @GetMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstacaoResponse>> consultarPorId(@PathVariable("id") Long estacaoId) {
        EstacaoResponse response = service.consultarPorId(estacaoId);

        EntityModel<EstacaoResponse> model = EntityModel.of(response);

        model.add(linkTo(methodOn(EstacaoController.class).consultarPorId(estacaoId)).withSelfRel());
        model.add(linkTo(EstacaoController.class).withRel("estacoes"));
        model.add(linkTo(LinhaController.class).withRel("linhas"));
        model.add(linkTo(OcupacaoController.class).slash("estacao").slash(estacaoId).withRel("ocupacoes"));
        model.add(linkTo(AlertaController.class).slash("estacao").slash(estacaoId).withRel("alertas"));

        return ResponseEntity.ok(model);
    }


    @PutMapping(path = "/{estacaoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstacaoResponse> atualizar(@PathVariable Long estacaoId, @Valid @RequestBody EstacaoRequest request) {
        EstacaoResponse EstacaoResponse = service.atualizar(estacaoId, request);
        return ResponseEntity.ok(EstacaoResponse);
    }

    @DeleteMapping(path = "/{estacaoId}")
    public ResponseEntity<Void> remover(@PathVariable Long estacaoId) {
        service.remover(estacaoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/filtrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<EstacaoResponse>> filtrar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String localizacao,
            @RequestParam(required = false) Boolean ativa,
            @RequestParam(required = false) Integer capacidadeMinima,
            Pageable pageable) {

        Page<EstacaoResponse> estacoes = service.filtrar(nome, localizacao, ativa, capacidadeMinima, pageable);

        return ResponseEntity.ok(estacoes);
    }

}
