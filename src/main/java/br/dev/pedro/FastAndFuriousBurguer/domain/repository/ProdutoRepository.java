/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.dev.pedro.FastAndFuriousBurguer.domain.repository;

import br.dev.pedro.FastAndFuriousBurguer.domain.model.CategoriaProduto;
import br.dev.pedro.FastAndFuriousBurguer.domain.model.Produto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sesi3dib
 */
@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long>{
    Optional<Produto> findByNome(String nome);
    Optional<Produto> findByNomeContaining(String nome);
    List<Produto> findByCategoriaProduto(CategoriaProduto categoriaProduto);
}
