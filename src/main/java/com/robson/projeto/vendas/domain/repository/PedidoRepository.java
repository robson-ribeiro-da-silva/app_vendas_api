package com.robson.projeto.vendas.domain.repository;

import ch.qos.logback.core.net.server.Client;
import com.robson.projeto.vendas.domain.entity.Cliente;
import com.robson.projeto.vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itemPedidos where p.id = :id")
    Optional<Pedido> findByIdFetchItemPedidos(@Param("id") Integer id);
}

