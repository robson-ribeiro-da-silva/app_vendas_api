package com.robson.projeto.vendas.rest.controller;

import com.robson.projeto.vendas.domain.entity.ItemPedido;
import com.robson.projeto.vendas.domain.entity.Pedido;
import com.robson.projeto.vendas.domain.entity.Produto;
import com.robson.projeto.vendas.domain.enums.StatusPedido;
import com.robson.projeto.vendas.domain.repository.ProdutoRepository;
import com.robson.projeto.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import com.robson.projeto.vendas.rest.dto.InformacoesItemPedidoDTO;
import com.robson.projeto.vendas.rest.dto.InformacoesPedidoDTO;
import com.robson.projeto.vendas.rest.dto.PedidoDTO;
import com.robson.projeto.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos/")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService
                .obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido())
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItemPedidos()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }

        return items
                .stream()
                .map(item -> InformacoesItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade()).build()
                ).collect(Collectors.toList());


    }


}
