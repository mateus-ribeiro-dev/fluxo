package br.com.fiap.fluxo.service;

import br.com.fiap.fluxo.dto.request.EstacaoRequest;
import br.com.fiap.fluxo.dto.response.EstacaoResponse;
import br.com.fiap.fluxo.entity.Estacao;
import br.com.fiap.fluxo.entity.Linha;
import br.com.fiap.fluxo.exception.EstacaoInexistenteException;
import br.com.fiap.fluxo.exception.LinhaInexistenteException;
import br.com.fiap.fluxo.repository.EstacaoRepository;
import br.com.fiap.fluxo.repository.LinhaRepository;
import br.com.fiap.fluxo.specification.EstacaoSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    @Transactional
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

    public Page<EstacaoResponse> listar(Pageable pageable) {
        return estacaoRepository.findAll(pageable)
                .map(EstacaoResponse::from);
    }

    public EstacaoResponse consultarPorId(Long id) {
        Optional<Estacao> estacaoCadastrada = estacaoRepository.findById(id);
        if (estacaoCadastrada.isPresent()) {
            return EstacaoResponse.from(estacaoCadastrada.get());
        }
        throw new EstacaoInexistenteException("Estação não encontrada: "+ id);
    }

    public EstacaoResponse atualizar(Long estacaoId, EstacaoRequest request) {
        Optional<Estacao> retornoConsulta = estacaoRepository.findById(estacaoId);
        if (retornoConsulta.isEmpty()) {
            throw new EstacaoInexistenteException("Estação não encontrada: " + estacaoId);
        }

        Estacao estacaoCadastrada = retornoConsulta.get();

        List<Linha> novasLinhas = linhaRepository.findAllById(request.linhasIds());

        if (novasLinhas.size() != request.linhasIds().size()) {
            throw new LinhaInexistenteException( "Uma ou mais linhas não foram encontradas");
        }

        estacaoCadastrada.setNome(request.nome());
        estacaoCadastrada.setLocalizacao(request.localizacao());
        estacaoCadastrada.setCapacidadeMaxima(request.capacidadeMaxima());
        estacaoCadastrada.setEstacaoAtiva(request.ativa());

        // Remove os relacionamentos anteriores
        for (Linha linha : estacaoCadastrada.getLinhas()) {
            linha.getEstacoes().remove(estacaoCadastrada);
        }

        // Atualiza os relacionamentos
        estacaoCadastrada.getLinhas().clear();
        estacaoCadastrada.getLinhas().addAll(novasLinhas);

        for (Linha linha : novasLinhas) {
            if (!linha.getEstacoes().contains(estacaoCadastrada)) {
                linha.getEstacoes().add(estacaoCadastrada);
            }
        }

        Estacao estacaoAtualizada = estacaoRepository.save(estacaoCadastrada);

        return EstacaoResponse.from(estacaoAtualizada);
    }

    public void remover(Long estacaoId) {
        Optional<Estacao> retornoConsulta = estacaoRepository.findById(estacaoId);
        if (retornoConsulta.isEmpty()) {
            throw new EstacaoInexistenteException("Estação não encontrada: " + estacaoId);
        }

        Estacao estacao = estacaoRepository.findById(estacaoId).orElseThrow(() ->
                        new EstacaoInexistenteException("Estação não encontrada: " + estacaoId));

        if (!estacao.getOcupacoes().isEmpty() || !estacao.getAlertas().isEmpty()) {
            throw new IllegalStateException("Não é possível excluir uma estação com histórico de ocupações ou alertas");
        }

        for (Linha linha : estacao.getLinhas()) {
            linha.getEstacoes().remove(estacao);
        }

        estacaoRepository.delete(estacao);
    }

    public Page<EstacaoResponse> filtrar(String nome, String localizacao, Boolean ativa, Integer capacidadeMinima, Pageable pageable) {

        Specification<Estacao> specification = Specification.unrestricted();

        if (nome != null && !nome.isBlank()) {
            specification = specification.and(EstacaoSpecification.nome(nome));
        }

        if (localizacao != null && !localizacao.isBlank()) {
            specification = specification.and(EstacaoSpecification.localizacao(localizacao));
        }

        if (ativa != null) {
            specification = specification.and(EstacaoSpecification.ativa(ativa));
        }

        if (capacidadeMinima != null) {
            specification = specification.and(EstacaoSpecification.capacidadeMinima(capacidadeMinima));
        }

        return estacaoRepository.findAll(specification, pageable).map(EstacaoResponse::from);
    }


}
