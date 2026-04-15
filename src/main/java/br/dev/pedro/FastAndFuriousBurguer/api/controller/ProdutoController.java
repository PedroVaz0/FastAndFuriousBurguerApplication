/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
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
    
    
    @PutMapping("/produto/{clienteID}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long produtoID,
            @RequestBody Produto produto) {
        
        
        if (!produtoRepository.existsById(produtoID)) {
            return ResponseEntity.notFound().build();
        }
        
        produto.setId(produtoID);
        produto = produtoRepository.save(produto);
        return ResponseEntity.ok(produto);
    }
     
}
