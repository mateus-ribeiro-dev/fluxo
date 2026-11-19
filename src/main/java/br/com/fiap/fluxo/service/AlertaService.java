package br.com.fiap.fluxo.service;

import br.com.fiap.fluxo.dto.response.AlertaResponse;
import br.com.fiap.fluxo.entity.Alerta;
import br.com.fiap.fluxo.entity.Ocupacao;
import br.com.fiap.fluxo.enums.NivelAlerta;
import br.com.fiap.fluxo.exception.AlertaInexistenteException;
import br.com.fiap.fluxo.repository.AlertaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public void gerarAlerta(Ocupacao ocupacao) {
        double percentual = ocupacao.getPercentualOcupacao();

        if (percentual <= 85) {
            return;
        }
        NivelAlerta nivel;

        if (percentual <= 90) {
            nivel = NivelAlerta.MODERADO;
        } else if (percentual <= 95) {
            nivel = NivelAlerta.ALTO;
        } else {
            nivel = NivelAlerta.CRITICO;
        }
        Alerta alerta = new Alerta();

        alerta.setEstacao(ocupacao.getEstacao());
        alerta.setOcupacao(ocupacao);
        alerta.setNivelAlerta(nivel);
        alerta.setMensagem("Superlotação na estação: " + ocupacao.getEstacao().getNome());
        alerta.setDataHora(LocalDateTime.now());

        alertaRepository.save(alerta);
    }

    @Transactional(readOnly = true)
    public Page<AlertaResponse> listar(Pageable pageable) {
        return alertaRepository.findAll(pageable)
                .map(AlertaResponse::from);
    }

    @Transactional(readOnly = true)
    public AlertaResponse consultarPorId(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new AlertaInexistenteException("Alerta não encontrado: " + id));

        return AlertaResponse.from(alerta);
    }

    @Transactional(readOnly = true)
    public Page<AlertaResponse> consultarPorNivel(NivelAlerta nivelAlerta, Pageable pageable) {
        return alertaRepository.findByNivelAlerta(nivelAlerta, pageable).map(AlertaResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AlertaResponse> consultarPorEstacao(Long estacaoId, Pageable pageable) {
        return alertaRepository.findByEstacaoId(estacaoId, pageable).map(AlertaResponse::from);
    }
}
