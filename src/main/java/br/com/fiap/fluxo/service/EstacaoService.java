package br.com.fiap.fluxo.service;

import br.com.fiap.fluxo.dto.request.EstacaoRequest;
import br.com.fiap.fluxo.dto.response.EstacaoResponse;
import br.com.fiap.fluxo.dto.response.LinhaResponse;
import br.com.fiap.fluxo.entity.Estacao;
import br.com.fiap.fluxo.entity.Linha;
import br.com.fiap.fluxo.exception.EstacaoInexistenteException;
import br.com.fiap.fluxo.exception.LinhaInexistenteException;
import br.com.fiap.fluxo.repository.EstacaoRepository;
import br.com.fiap.fluxo.repository.LinhaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstacaoService {

    private final EstacaoRepository estacaoRepository;
    private final LinhaRepository linhaRepository;

    public EstacaoService(EstacaoRepository estacaoRepository, LinhaRepository linhaRepository) {
        this.estacaoRepository = estacaoRepository;
        this.linhaRepository = linhaRepository;
    }

    public EstacaoResponse cadastrar(EstacaoRequest estacaoRequest) {
        Estacao estacao = estacaoRequest.toEntity();

        List<Linha> linhas = linhaRepository.findAllById(estacaoRequest.linhasIds());

        if (linhas.size() != estacaoRequest.linhasIds().size()) {
            throw new LinhaInexistenteException("Uma ou mais linhas não foram encontradas");
        }

        estacao.setLinhas(linhas);

        Estacao novaEstacao = estacaoRepository.save(estacao);

        for (Linha linha : linhas) {
            linha.getEstacoes().add(novaEstacao);
        }

        return EstacaoResponse.from(novaEstacao);
    }

    public List<EstacaoResponse> listar(Pageable pageable) {
        List<EstacaoResponse> estacoesResponse = new ArrayList<>();

        Page<Estacao> estacoesCadastradas = estacaoRepository.findAll(pageable);

        for (Estacao estacaoCadastrada : estacoesCadastradas) {
            EstacaoResponse estacaoResponse = EstacaoResponse.from(estacaoCadastrada);
            estacoesResponse.add(estacaoResponse);
        }

        return estacoesResponse;
    }

    public EstacaoResponse consultarPorId(Long id) {
        Optional<Estacao> estacaoCadastrada = estacaoRepository.findById(id);
        if (estacaoCadastrada.isPresent()) {
            return EstacaoResponse.from(estacaoCadastrada.get());
        }
        throw new EstacaoInexistenteException("Estação não encontrada: "+ id);
    }
}
