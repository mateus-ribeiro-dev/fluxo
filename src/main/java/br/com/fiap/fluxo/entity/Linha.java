package br.com.fiap.fluxo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "linha_sec", allocationSize = 1)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "linha_estacao",
            joinColumns = @JoinColumn(name = "linha_id"),
            inverseJoinColumns = @JoinColumn(name = "estacao_id")
    )
    private List<Estacao> estacoes;

    private String nome;
    private int codigo;
    private String cor;
    private Boolean isLinhaAtiva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Estacao> getEstacoes() {
        return estacoes;
    }

    public void setEstacoes(List<Estacao> estacoes) {
        this.estacoes = estacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Boolean getLinhaAtiva() {
        return isLinhaAtiva;
    }

    public void setLinhaAtiva(Boolean linhaAtiva) {
        isLinhaAtiva = linhaAtiva;
    }
}
