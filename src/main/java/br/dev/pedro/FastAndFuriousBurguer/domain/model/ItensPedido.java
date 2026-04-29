/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author sesi3dib
 */

@Entity
public class ItensPedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private int qtd;
    private Double valUnit;
    private String obs; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Double getValUnit() {
        return valUnit;
    }

    public void setValUnit(Double valUnit) {
        this.valUnit = valUnit;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public ItensPedido(Long id, int qtd, Double valUnit, String obs) {
        this.id = id;
        this.qtd = qtd;
        this.valUnit = valUnit;
        this.obs = obs;
    }

    public ItensPedido() {
    }
}
