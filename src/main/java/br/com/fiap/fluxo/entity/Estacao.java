package br.com.fiap.fluxo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Estacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "estacao_sec", allocationSize = 1)
    private Long id;

    private String nome;
    private String localizacao;
    private int capacidadeMaxima;
    private Boolean isEstacaoAtiva;

    @ManyToMany(mappedBy = "estacoes")
    private List<Linha> linhas = new ArrayList<>();

    @OneToMany(mappedBy = "estacao")
    private List<Ocupacao> ocupacoes = new ArrayList<>();

    @OneToMany(mappedBy = "estacao")
    private List<Alerta> alertas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Boolean getEstacaoAtiva() {
        return isEstacaoAtiva;
    }

    public void setEstacaoAtiva(Boolean estacaoAtiva) {
        isEstacaoAtiva = estacaoAtiva;
    }

    public List<Linha> getLinhas() {
        return linhas;
    }

    public void setLinhas(List<Linha> linhas) {
        this.linhas = linhas;
    }

    public List<Ocupacao> getOcupacoes() {
        return ocupacoes;
    }

    public void setOcupacoes(List<Ocupacao> ocupacoes) {
        this.ocupacoes = ocupacoes;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }
}
