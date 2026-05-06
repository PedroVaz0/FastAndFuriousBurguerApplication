package br.dev.pedro.FastAndFuriousBurguer.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double preco;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private CategoriaProduto categoriaProduto;

    public Produto() {}

    public Produto(Long id, String nome, Double preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Produto(Long id, String nome, Double preco, String descricao, CategoriaProduto categoriaProduto) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoriaProduto = categoriaProduto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public CategoriaProduto getCategoriaProduto() { return categoriaProduto; }
    public void setCategoriaProduto(CategoriaProduto categoriaProduto) { this.categoriaProduto = categoriaProduto; }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Produto other = (Produto) obj;
        return this.id.equals(other.id); // ← corrigido
    }
}