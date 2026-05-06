package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.PedidoDTO.ProdutoRequestDTO;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.CategoriaProduto;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.service.ProdutoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService; // ← apenas o service, sem repository

    @GetMapping
    public List<Produto> listar() {
        return produtoService.listar();
    }

    @GetMapping("/{produtoID}")
    public ResponseEntity<Produto> buscar(@PathVariable Long produtoID) {
        Optional<Produto> produto = produtoService.buscar(produtoID);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@Valid @RequestBody ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoriaProduto(dto.getCategoriaProduto());
        return produtoService.adicionar(produto);
    }

    @PutMapping("/{produtoID}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long produtoID,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        Produto dadosNovos = new Produto();
        dadosNovos.setNome(dto.getNome());
        dadosNovos.setPreco(dto.getPreco());
        dadosNovos.setDescricao(dto.getDescricao());
        dadosNovos.setCategoriaProduto(dto.getCategoriaProduto());
        return ResponseEntity.ok(produtoService.atualizar(produtoID, dadosNovos));
    }

    @DeleteMapping("/{produtoID}")
    public ResponseEntity<Void> excluir(@PathVariable Long produtoID) {
        produtoService.excluir(produtoID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cat/{categoriaProduto}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(
            @PathVariable CategoriaProduto categoriaProduto) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoriaProduto);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
}