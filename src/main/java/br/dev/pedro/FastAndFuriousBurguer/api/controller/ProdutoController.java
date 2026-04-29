package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.CategoriaProduto;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import br.dev.pedro.FastAndFuriousBurguer.domain.service.ProdutoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")  // corrigido: estava com ´ em vez de /
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{produtoID}")
    public ResponseEntity<Produto> buscar(@PathVariable Long produtoID) {
        Optional<Produto> produto = produtoRepository.findById(produtoID);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{produtoID}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long produtoID,
            @Valid @RequestBody Produto produto
    ) {
        Optional<Produto> produtoExistente = produtoRepository.findById(produtoID);
        if (produtoExistente.isPresent()) {
            Produto produtoAtualizado = produtoService.atualizar(produtoID, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{produtoID}")  // corrigido: estava faltando } no fim
    public ResponseEntity<Void> excluir(@PathVariable Long produtoID) {
        produtoService.excluir(produtoID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cat/{categoriaProduto}")
    public ResponseEntity<List<Produto>> buscarCat(@PathVariable CategoriaProduto categoriaProduto) {
        List<Produto> produtos = produtoRepository.findByCategoriaProduto(categoriaProduto);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(produtos);
        }
    }
}