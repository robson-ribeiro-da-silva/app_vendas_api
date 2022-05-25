package com.robson.projeto.vendas.domain.repository;

import com.robson.projeto.vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeOrId(String nome, Integer id);

    boolean existsByNome(String nome);

    @Modifying
    void deleteByNome(String nome);

    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNomeQuery(@Param("nome") String nome);

    @Query(value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNomeQueryNativa(@Param("nome") String nome);

    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
