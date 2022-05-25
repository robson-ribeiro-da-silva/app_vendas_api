package com.robson.projeto.vendas.rest.controller;

import com.robson.projeto.vendas.domain.entity.Cliente;
import com.robson.projeto.vendas.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes/")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("{id}")
    public Cliente getById(@PathVariable("id") Integer id){
       return clienteRepository
               .findById(id)
               .orElseThrow(() ->
                       new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente){
       return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        clienteRepository
                .findById(id)
                .map( cliente -> { clienteRepository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @RequestBody Cliente cliente){

        clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> find(Cliente cliente){

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(cliente, matcher);
        return clienteRepository.findAll(example);
    }

    /*@GetMapping("{id}")
    public ResponseEntity getClienteById(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            clienteRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Cliente cliente){
        Optional<Cliente> clienteSalvo = clienteRepository.findById(id);

        if(clienteSalvo.isPresent()){
            cliente.setId(clienteSalvo.get().getId());
            clienteRepository.save(cliente);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

        *//*return clienteRepository
                .findById(id)
                .map(clienteExistente ->{
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );*//*
    }

    @GetMapping
    public ResponseEntity find(Cliente cliente){

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(cliente, matcher);
        List<Cliente> clientes = clienteRepository.findAll(example);
        return ResponseEntity.ok(clientes);
    }*/

}
