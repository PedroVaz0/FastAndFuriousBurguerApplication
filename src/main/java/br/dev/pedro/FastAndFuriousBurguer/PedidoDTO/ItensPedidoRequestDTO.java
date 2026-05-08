/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.PedidoDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 *
 * @author sesi3dib
 */
public class ItensPedidoRequestDTO {
    @NotNull (message = "Produto é obrigatório")
    private Long produtoId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer qtd;
    
    private String obs;
    
    public Long getProdutoId() {return produtoId;}
    public void setProdutoId(Long produtoId) {this.produtoId = produtoId;}
    
    public Integer getQtd() {return qtd;}
    public void setQtd(Integer qtd) {this.qtd = qtd;}
    
    public String getObs() {return obs;}
    public void setObs(String obs) {this.obs = obs;}
}
