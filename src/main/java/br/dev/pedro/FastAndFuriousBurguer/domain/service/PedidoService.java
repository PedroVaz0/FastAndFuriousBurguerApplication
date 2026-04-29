/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.domain.service;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sesi3dib
 */
@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido atualizarStatus(Long pedidoID, Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoID)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedidoExistente.setStatus(pedido.getStatus());
        return pedidoRepository.save(pedidoExistente);
    }
}
