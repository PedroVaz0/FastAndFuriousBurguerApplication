/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.domain.service;

import br.dev.pedro.FastAndFuriousBurguer.domain.exception.DomainException;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscar(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido adicionar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Long id, Pedido pedido) {
        if (!pedidoRepository.existsById(id)) {
            throw new DomainException("Pedido não encontrado");
        }
        pedido.setId(id);
        return pedidoRepository.save(pedido);
    }

    public void excluir(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new DomainException("Pedido não encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    public Pedido atualizarStatus(Long id, Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Pedido não encontrado"));
        pedidoExistente.setStatus(pedido.getStatus());
        return pedidoRepository.save(pedidoExistente);
    }
}
