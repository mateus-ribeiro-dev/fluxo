package br.com.fiap.fluxo.service;

import br.com.fiap.fluxo.dto.request.OcupacaoRequest;
import br.com.fiap.fluxo.dto.response.EstacaoResponse;
import br.com.fiap.fluxo.dto.response.OcupacaoResponse;
import br.com.fiap.fluxo.entity.Estacao;
import br.com.fiap.fluxo.entity.Ocupacao;
import br.com.fiap.fluxo.exception.EstacaoInexistenteException;
import br.com.fiap.fluxo.repository.EstacaoRepository;
import br.com.fiap.fluxo.repository.OcupacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OcupacaoService {
    
    private final OcupacaoRepository ocupacaoRepository;
    private final EstacaoRepository estacaoRepository;
    private final AlertaService alertaService;

    public OcupacaoService(OcupacaoRepository ocupacaoRepository, EstacaoRepository estacaoRepository, AlertaService alertaService) {
        this.ocupacaoRepository = ocupacaoRepository;
        this.estacaoRepository = estacaoRepository;
        this.alertaService = alertaService;
    }

    @Transactional
    public OcupacaoResponse cadastrar(OcupacaoRequest request) {

        Estacao estacao = estacaoRepository
                .findById(request.estacaoId())
                .orElseThrow(() -> new EstacaoInexistenteException("Estação não encontrada: " + request.estacaoId()));

        if (estacao.getCapacidadeMaxima() <= 0) {
            throw new RuntimeException("A capacidade da estação deve ser positiva");
        }

        Ocupacao anterior = ocupacaoRepository
                .findFirstByEstacaoIdOrderByDataHoraDescIdDesc(estacao.getId())
                .orElse(null);

        int ocupacaoAnterior = anterior == null
                ? 0
                : anterior.getOcupacaoAtual();

        long novaOcupacao = (long) ocupacaoAnterior
                + request.entradas() - request.saidas();

        if (novaOcupacao < 0 || novaOcupacao > Integer.MAX_VALUE) {
            throw new RuntimeException("Ocupação inválida");
        }

        double percentual = novaOcupacao * 100.0 / estacao.getCapacidadeMaxima();

        Ocupacao ocupacao = request.toEntity();

        ocupacao.setEstacao(estacao);
        ocupacao.setOcupacaoAtual((int)novaOcupacao);
        ocupacao.setPercentualOcupacao(percentual);
        ocupacao.setDataHora(LocalDateTime.now());

        Ocupacao novaMedicao = ocupacaoRepository.save(ocupacao);

        alertaService.gerarAlerta(novaMedicao);

        return OcupacaoResponse.from(novaMedicao);
    }

    public Page<OcupacaoResponse> listar(Pageable pageable) {
        return ocupacaoRepository.findAll(pageable)
                .map(OcupacaoResponse::from);
    }

}
