/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.PedidoDTO.PedidoRequestDTO;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.service.PedidoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;      // ← só o service, sem repository

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listar();
    }

    @GetMapping("/{pedidoID}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long pedidoID) {
        Optional<Pedido> pedido = pedidoService.buscar(pedidoID);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido adicionar(@Valid @RequestBody PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNome(dto.getNome());
        pedido.setCpf(dto.getCpf());
        pedido.setStatus(dto.getStatus());
        return pedidoService.adicionar(pedido);
    }

    @PutMapping("/{pedidoID}")
public ResponseEntity<Pedido> atualizar(@PathVariable Long pedidoID,
        @Valid @RequestBody PedidoRequestDTO dto) {
    Pedido pedido = new Pedido();
    pedido.setNome(dto.getNome());
    pedido.setCpf(dto.getCpf());
    pedido.setStatus(dto.getStatus());
    return ResponseEntity.ok(pedidoService.atualizar(pedidoID, pedido));
}

    @DeleteMapping("/{pedidoID}")
    public ResponseEntity<Void> excluir(@PathVariable Long pedidoID) {
        pedidoService.excluir(pedidoID);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pedidoID}/status")
public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long pedidoID,
        @Valid @RequestBody PedidoRequestDTO dto) {
    Pedido pedido = new Pedido();
    pedido.setStatus(dto.getStatus());
    return ResponseEntity.ok(pedidoService.atualizarStatus(pedidoID, pedido));
}
}
