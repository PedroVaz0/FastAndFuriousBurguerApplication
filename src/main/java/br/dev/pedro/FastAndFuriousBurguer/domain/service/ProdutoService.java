/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.domain.service;

import br.dev.pedro.FastAndFuriousBurguer.domain.exception.DomainException;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author sesi3dib
 */
@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    public Produto atualizar(Long id, Produto dadosNovos) {
        Produto produtoAtual = produtoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Produto não encontrado"));
        
        if(dadosNovos.getNome() != null) {
            Optional<Produto> produtoExistente = produtoRepository.findByNome(dadosNovos.getNome());
            
            if (produtoExistente.isPresent() && !produtoExistente.get().getId().equals(id)){
                throw new DomainException("Ja existe um produto com esse nome");
            }
            
            produtoAtual.setNome(dadosNovos.getNome());
        }
        
        if (dadosNovos.getPreco() != null) {
            produtoAtual.setPreco(dadosNovos.getPreco());
        }
        
        if (dadosNovos.getIngredientes() != null) {
            produtoAtual.setIngredientes(dadosNovos.getIngredientes());
        }
        
        return produtoRepository.save(produtoAtual);
    }
    
    
    
    @DeleteMapping("/produto/{produtoID}")
    public ResponseEntity<Void> excluir(@PathVariable Long produtoID) {
        
        if (!produtoRepository.existsById(produtoID)) {
            return ResponseEntity.notFound().build();
        }
        
        produtoRepository.deleteById(produtoID);
        return ResponseEntity.noContent().build();
    }
}
