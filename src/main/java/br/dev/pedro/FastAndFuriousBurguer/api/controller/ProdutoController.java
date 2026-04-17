/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import br.dev.pedro.FastAndFuriousBurguer.domain.service.ProdutoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.notFound;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sesi3dib
 */

@RestController
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired 
    private ProdutoService produtoService;
    
    @GetMapping("/produto")
    public List<Produto> listas() {
        return produtoRepository.findAll();
    }
    
    
    @GetMapping("/produto/{produtoID}")
    public ResponseEntity<Produto> buscar(@PathVariable Long produtoID) {
        Optional<Produto> produto = produtoRepository.findById(produtoID);
        
        if(produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    
    @PostMapping("/produto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }
    
    
    @PutMapping("/produto/{produtoID}")
public ResponseEntity<Produto> atualizar(
    @PathVariable Long produtoID, 
    @Valid @RequestBody Produto produto
) {
    // 1. Attempt to find the existing record using Optional
    Optional<Produto> produtoExistente = produtoRepository.findById(produtoID);

    // 2. Use the logic style from your 'buscar' method
    if (produtoExistente.isPresent()) {
        // Here we call the service to handle the actual update logic
        Produto produtoAtualizado = produtoService.atualizar(produtoID, produto);
        return ResponseEntity.ok(produtoAtualizado);
    } else {
        // If it doesn't exist, return 404
        return ResponseEntity.notFound().build();
    }
}
     
}
