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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaService {
    private final LinhaRepository linhaRepository;

    public LinhaService(LinhaRepository linhaRepository) {
        this.linhaRepository = linhaRepository;
    }

    @Transactional
    public LinhaResponse cadastrar(LinhaRequest linhaRequest) {
        Linha novaLinha = linhaRequest.toEntity();
        Linha salva = linhaRepository.save(novaLinha);

        return LinhaResponse.from(salva);
    }

    public List<LinhaResponse> listar(Pageable pageable) {
        List<LinhaResponse> linhasResponse = new ArrayList<>();

        Page<Linha> linhasCadastradas = linhaRepository.findAll(pageable);

        for (Linha linhaCadastrada : linhasCadastradas) {
            LinhaResponse linhaResponse = LinhaResponse.from(linhaCadastrada);
            linhasResponse.add(linhaResponse);
        }

        return linhasResponse;
    }

    public LinhaResponse consultarPorId(Long id) {
        Optional<Linha> linhaCadastrada = linhaRepository.findById(id);
        if (linhaCadastrada.isPresent()) {
            return LinhaResponse.from(linhaCadastrada.get());
        }
        throw new LinhaInexistenteException("Linha não encontrada: "+ id);
    }
}
