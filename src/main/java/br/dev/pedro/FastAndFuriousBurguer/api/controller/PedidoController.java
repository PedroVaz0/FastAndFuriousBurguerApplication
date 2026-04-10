/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.api.controller;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.Pedido;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sesi3dib
 */
@RestController
public class PedidoController {
    
    List<Pedido> listaPedidos;
    
    @GetMapping ("/pedido")
    public List<Pedido> listas() {
        
        listaPedidos = new ArrayList<Pedido>();
        listaPedidos.add(new Pedido(1, "Pedro", "23405429584"));
        listaPedidos.add(new Pedido(1, "Julio", "32949584930"));
        listaPedidos.add(new Pedido(1, "Maiara", "38495739584"));
        
        return listaPedidos;
        
        
        
    }
}
