package br.com.fiap.fluxo.service;

import br.com.fiap.fluxo.entity.Alerta;
import br.com.fiap.fluxo.entity.Ocupacao;
import br.com.fiap.fluxo.enums.NivelAlerta;
import br.com.fiap.fluxo.repository.AlertaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
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
}
