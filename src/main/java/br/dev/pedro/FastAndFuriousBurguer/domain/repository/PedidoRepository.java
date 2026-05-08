package br.dev.pedro.FastAndFuriousBurguer.domain.repository;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.StatusPedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByNome(String nome);
    List<Pedido> findByNomeContaining(String nome);
    List<Pedido> findByStatus(StatusPedido status); 
}