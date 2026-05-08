package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.PedidoDTO.PedidoRequestDTO;
import br.dev.pedro.FastAndFuriousBurguer.PedidoDTO.StatusPedidoDTO;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.StatusPedido;
import br.dev.pedro.FastAndFuriousBurguer.domain.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fastfurious/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Get all orders", description = "Returns a list of all orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listar();
    }

    @Operation(summary = "Get an order by id", description = "Returns an order as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "404", description = "Not found - The order was not found")
    })
    @GetMapping("/{pedidoID}")
    public ResponseEntity<Pedido> buscar(
            @PathVariable("pedidoID") @Parameter(name = "pedidoID", description = "Order id", example = "1") Long pedidoID) {
        Optional<Pedido> pedido = pedidoService.buscar(pedidoID);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get orders by status", description = "Returns a list of orders filtered by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "204", description = "No content - No orders found for the given status")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> buscarPorStatus(
            @PathVariable("status") @Parameter(name = "status", description = "Order status", example = "ABERTO") StatusPedido status) {
        List<Pedido> pedidos = pedidoService.buscarPorStatus(status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Create a new order", description = "Creates a new order with status ABERTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created"),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid data or product not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido adicionar(@Valid @RequestBody PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNome(dto.getNome());
        pedido.setCpf(dto.getCpf());
        return pedidoService.adicionar(pedido, dto.getItens());
    }

    @Operation(summary = "Update an order by id", description = "Updates the name and CPF of an order as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated"),
        @ApiResponse(responseCode = "400", description = "Bad request - Order not found")
    })
    @PutMapping("/{pedidoID}")
    public ResponseEntity<Pedido> atualizar(
            @PathVariable("pedidoID") @Parameter(name = "pedidoID", description = "Order id", example = "1") Long pedidoID,
            @Valid @RequestBody PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNome(dto.getNome());
        pedido.setCpf(dto.getCpf());
        return ResponseEntity.ok(pedidoService.atualizar(pedidoID, pedido));
    }

    @Operation(summary = "Cancel an order by id", description = "Cancels an order by setting its status to CANCELADO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully cancelled"),
        @ApiResponse(responseCode = "400", description = "Bad request - Order not found or order already delivered")
    })
    @DeleteMapping("/{pedidoID}")
    public ResponseEntity<Pedido> excluir(
            @PathVariable("pedidoID") @Parameter(name = "pedidoID", description = "Order id", example = "1") Long pedidoID) {
        return ResponseEntity.ok(pedidoService.excluir(pedidoID));
    }

    @Operation(summary = "Update the status of an order by id", description = "Updates the status of an order as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated"),
        @ApiResponse(responseCode = "400", description = "Bad request - Order not found or invalid status")
    })
    @PutMapping("/status/{pedidoID}")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable("pedidoID") @Parameter(name = "pedidoID", description = "Order id", example = "1") Long pedidoID,
            @Valid @RequestBody StatusPedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setStatus(dto.getStatus());
        return ResponseEntity.ok(pedidoService.atualizarStatus(pedidoID, pedido));
    }
}