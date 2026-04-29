/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import br.dev.pedro.FastAndFuriousBurguer.domain.service.PedidoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class PedidoController {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private PedidoService pedidoService;
    
    
    @GetMapping ("/pedido")
    public List<Pedido> listas() {
        
        return pedidoRepository.findAll();
        //return pedidoRepository.findByNome("Riquelme");
        //return pedidoRepository.findByNomeContaining("Maiara");
    }
    
    
    @GetMapping ("/pedido/{pedidoID}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long pedidoID) {
        Optional<Pedido> pedido = pedidoRepository.findById(pedidoID);
        
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @PostMapping("/pedido")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido adicionar(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
    
    @PutMapping("/pedido/{pedidoID}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long pedidoID,
            @RequestBody Pedido pedido) {
        if (!pedidoRepository.existsById(pedidoID)) {
            return ResponseEntity.notFound().build();
        }
        
        pedido.setId(pedidoID);
        pedido = pedidoRepository.save(pedido);
        return ResponseEntity.ok(pedido);
    }
    
    
    
    
    @DeleteMapping("/pedido/{pedidoID}")
    public ResponseEntity<Void> excluir(@PathVariable Long pedidoID) {
        if (!pedidoRepository.existsById(pedidoID)) {
            return ResponseEntity.notFound().build();
        }
        
        pedidoRepository.deleteById(pedidoID);
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/pedido/status/{id}")
    public ResponseEntity<Pedido> buscarStatus(@PathVariable Long id) {
           Optional<Pedido> pedido = pedidoRepository.findById(id);

           if (pedido.isPresent()) {
               return ResponseEntity.ok(pedido.get());
           } else {
               return ResponseEntity.notFound().build();
           }
       }
    
    
        @PutMapping("/pedido/status/{pedidoID}")
        public ResponseEntity<Pedido> atualizarStatus(
        @PathVariable Long pedidoID,
        @Valid @RequestBody Pedido pedido
    ) {
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(pedidoID);

        if (pedidoExistente.isPresent()) {
            Pedido pedidoAtualizado = pedidoService.atualizarStatus(pedidoID, pedido);
            return ResponseEntity.ok(pedidoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    }
