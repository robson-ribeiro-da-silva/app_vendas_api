package com.robson.projeto.vendas.service;

import com.robson.projeto.vendas.domain.entity.Pedido;
import com.robson.projeto.vendas.domain.enums.StatusPedido;
import com.robson.projeto.vendas.rest.dto.PedidoDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
