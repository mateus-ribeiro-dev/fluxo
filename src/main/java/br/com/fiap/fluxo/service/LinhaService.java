package br.com.fiap.fluxo.service;

import br.com.fiap.fluxo.dto.request.LinhaRequest;
import br.com.fiap.fluxo.dto.response.LinhaResponse;
import br.com.fiap.fluxo.entity.Linha;
import br.com.fiap.fluxo.exception.LinhaInexistenteException;
import br.com.fiap.fluxo.repository.LinhaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Optional;

@Service
public class LinhaService {
    private final LinhaRepository linhaRepository;

    public LinhaService(LinhaRepository linhaRepository) {
        this.linhaRepository = linhaRepository;
    }

    @CacheEvict(value = {"linhas", "estacoes"}, allEntries = true)
    @Transactional
    public LinhaResponse cadastrar(LinhaRequest linhaRequest) {
        Linha novaLinha = linhaRequest.toEntity();
        Linha salva = linhaRepository.save(novaLinha);

        return LinhaResponse.from(salva);
    }

    public Page<LinhaResponse> listar(Pageable pageable) {
        return linhaRepository.findAll(pageable)
                .map(LinhaResponse::from);
    }

    @Cacheable(value = "linhas", key = "#id")
    public LinhaResponse consultarPorId(Long id) {
        Optional<Linha> linhaCadastrada = linhaRepository.findById(id);
        if (linhaCadastrada.isPresent()) {
            return LinhaResponse.from(linhaCadastrada.get());
        }
        throw new LinhaInexistenteException("Linha não encontrada: "+ id);
    }

    @CacheEvict(value = {"linhas", "estacoes"}, allEntries = true)
    public LinhaResponse atualizar(Long linhaId, LinhaRequest request) {
        Optional<Linha> retornoConsulta = linhaRepository.findById(linhaId);

        if (retornoConsulta.isEmpty()) {
            throw new LinhaInexistenteException("Linha não encontrada: " + linhaId);
        }

        Linha linhaCadastrada = retornoConsulta.get();

        linhaCadastrada.setNome(request.nome());
        linhaCadastrada.setCodigo(request.codigo());
        linhaCadastrada.setCor(request.cor());
        linhaCadastrada.setLinhaAtiva(request.ativa());

        Linha linhaAtualizada = linhaRepository.save(linhaCadastrada);

        return LinhaResponse.from(linhaAtualizada);
    }

    @CacheEvict(value = {"linhas", "estacoes"}, allEntries = true)
    public void remover(Long linhaId) {
            Optional<Linha> retornoConsulta = linhaRepository.findById(linhaId);
            if (retornoConsulta.isEmpty()) {
                throw new LinhaInexistenteException("Linha não encontrada: " + linhaId);
            }

            linhaRepository.deleteById(linhaId);
    }


}
