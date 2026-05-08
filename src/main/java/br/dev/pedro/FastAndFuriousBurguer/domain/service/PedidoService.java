package br.dev.pedro.FastAndFuriousBurguer.domain.service;

import br.dev.pedro.FastAndFuriousBurguer.PedidoDTO.ItensPedidoRequestDTO;
import br.dev.pedro.FastAndFuriousBurguer.domain.exception.DomainException;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.ItensPedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.StatusPedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.PedidoRepository;
import br.dev.pedro.FastAndFuriousBurguer.domain.repository.ProdutoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscar(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> buscarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public Pedido adicionar(Pedido pedido, List<ItensPedidoRequestDTO> itensDTO) {
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setDataCriacao(LocalDateTime.now());

        if (itensDTO != null) {
            for (ItensPedidoRequestDTO itemDTO : itensDTO) {
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                        .orElseThrow(() -> new DomainException("Produto não encontrado:" + itemDTO.getProdutoId()));

                ItensPedido item = new ItensPedido();
                item.setPedido(pedido);
                item.setProduto(produto);
                item.setQtd(itemDTO.getQtd());
                item.setValUnit(produto.getPreco());
                item.setObs(itemDTO.getObs());

                pedido.getItens().add(item);
            }
        }
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Long id, Pedido pedido) {
        if (!pedidoRepository.existsById(id)) {
            throw new DomainException("Pedido não encontrado");
        }
        pedido.setId(id);
        return pedidoRepository.save(pedido);
    }

    public Pedido excluir(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Pedido não encontrado"));

        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new DomainException("Pedido já entregue não pode ser cancelado");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizarStatus(Long id, Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Pedido não encontrado"));

        pedidoExistente.setStatus(pedido.getStatus());

        if (pedido.getStatus() == StatusPedido.ENTREGUE
                || pedido.getStatus() == StatusPedido.CANCELADO) {
            pedidoExistente.setDataFechamento(LocalDateTime.now());
        }

        return pedidoRepository.save(pedidoExistente);
    }
}
