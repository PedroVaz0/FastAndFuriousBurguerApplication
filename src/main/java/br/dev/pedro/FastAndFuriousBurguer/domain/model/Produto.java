/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author sesi3dib
 */
@Entity
public class Produto {
    @Id
    private long id;
    private String nome;
    private double preco;
    private String ingredientes;
}
