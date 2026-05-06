package br.dev.pedro.FastAndFuriousBurguer.PedidoDTO;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.StatusPedido;
import jakarta.validation.constraints.NotNull;

public class StatusPedidoDTO {

    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
}