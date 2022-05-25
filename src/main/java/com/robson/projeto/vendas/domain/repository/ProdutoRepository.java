package com.robson.projeto.vendas.domain.repository;

import com.robson.projeto.vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
