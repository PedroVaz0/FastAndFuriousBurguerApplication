package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.PedidoDTO.ProdutoRequestDTO;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.CategoriaProduto;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import br.dev.pedro.FastAndFuriousBurguer.domain.service.ProdutoService;
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
@RequestMapping("fastfurious/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping
    public List<Produto> listar() {
        return produtoService.listar();
    }

    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/{produtoID}")
    public ResponseEntity<Produto> buscar(
            @PathVariable("produtoID") @Parameter(name = "produtoID", description = "Product id", example = "1") Long produtoID) {
        Optional<Produto> produto = produtoService.buscar(produtoID);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new product", description = "Creates a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created"),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid data or duplicate product name")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@Valid @RequestBody ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoriaProduto(dto.getCategoriaProduto());
        return produtoService.adicionar(produto);
    }

    @Operation(summary = "Update a product by id", description = "Updates a product as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated"),
        @ApiResponse(responseCode = "400", description = "Bad request - Product not found or duplicate product name")
    })
    @PutMapping("/{produtoID}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable("produtoID") @Parameter(name = "produtoID", description = "Product id", example = "1") Long produtoID,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        Produto dadosNovos = new Produto();
        dadosNovos.setNome(dto.getNome());
        dadosNovos.setPreco(dto.getPreco());
        dadosNovos.setDescricao(dto.getDescricao());
        dadosNovos.setCategoriaProduto(dto.getCategoriaProduto());
        return ResponseEntity.ok(produtoService.atualizar(produtoID, dadosNovos));
    }

    @Operation(summary = "Delete a product by id", description = "Deletes a product as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted"),
        @ApiResponse(responseCode = "400", description = "Bad request - Product not found")
    })
    @DeleteMapping("/{produtoID}")
    public ResponseEntity<Void> excluir(
            @PathVariable("produtoID") @Parameter(name = "produtoID", description = "Product id", example = "1") Long produtoID) {
        produtoService.excluir(produtoID);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get products by category", description = "Returns a list of products as per the category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "204", description = "No content - No products found for the given category")
    })
    @GetMapping("/cat/{categoriaProduto}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(
            @PathVariable("categoriaProduto") @Parameter(name = "categoriaProduto", description = "Product category", example = "LANCHES") CategoriaProduto categoriaProduto) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoriaProduto);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
}