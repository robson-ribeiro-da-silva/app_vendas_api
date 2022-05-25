package com.robson.projeto.vendas.service.impl;

import com.robson.projeto.vendas.domain.entity.Cliente;
import com.robson.projeto.vendas.domain.entity.ItemPedido;
import com.robson.projeto.vendas.domain.entity.Pedido;
import com.robson.projeto.vendas.domain.entity.Produto;
import com.robson.projeto.vendas.domain.enums.StatusPedido;
import com.robson.projeto.vendas.domain.repository.ClienteRepository;
import com.robson.projeto.vendas.domain.repository.ItemPedidoRepository;
import com.robson.projeto.vendas.domain.repository.PedidoRepository;
import com.robson.projeto.vendas.domain.repository.ProdutoRepository;
import com.robson.projeto.vendas.exception.PedidoNaoEncontradoException;
import com.robson.projeto.vendas.exception.RegraNegocioException;
import com.robson.projeto.vendas.rest.dto.ItemPedidoDTO;
import com.robson.projeto.vendas.rest.dto.PedidoDTO;
import com.robson.projeto.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idcliente = dto.getCliente();
        Cliente cliente = clienteRepository.findById(idcliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
        pedido.setItemPedidos(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItemPedidos(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());

    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idproduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idproduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: "+idproduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
        }).collect(Collectors.toList());

    }
}
