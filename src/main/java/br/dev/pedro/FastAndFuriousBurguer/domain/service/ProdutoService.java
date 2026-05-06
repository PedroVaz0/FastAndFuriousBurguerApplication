package br.dev.pedro.FastAndFuriousBurguer.domain.service;

import br.dev.pedro.FastAndFuriousBurguer.domain.exception.DomainException;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.CategoriaProduto;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscar(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> buscarPorCategoria(CategoriaProduto categoria) {
        return produtoRepository.findByCategoriaProduto(categoria);
    }

    public Produto adicionar(Produto produto) {
        produtoRepository.findByNome(produto.getNome()).ifPresent(p -> {
            throw new DomainException("Já existe um produto com esse nome");
        });
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto dadosNovos) {
        Produto produtoAtual = produtoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Produto não encontrado"));

        if (dadosNovos.getNome() != null) {
            Optional<Produto> produtoExistente = produtoRepository.findByNome(dadosNovos.getNome());
            if (produtoExistente.isPresent() && !produtoExistente.get().getId().equals(id)) {
                throw new DomainException("Já existe um produto com esse nome");
            }
            produtoAtual.setNome(dadosNovos.getNome());
        }

        if (dadosNovos.getPreco() != null) {
            produtoAtual.setPreco(dadosNovos.getPreco());
        }

        if (dadosNovos.getDescricao() != null) {
            produtoAtual.setDescricao(dadosNovos.getDescricao());
        }

        if (dadosNovos.getCategoriaProduto() != null) {
            produtoAtual.setCategoriaProduto(dadosNovos.getCategoriaProduto());
        }

        return produtoRepository.save(produtoAtual);
    }

    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new DomainException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}