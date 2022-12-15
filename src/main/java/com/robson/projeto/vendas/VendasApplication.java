package com.robson.projeto.vendas;

import com.robson.projeto.vendas.domain.entity.Cliente;
import com.robson.projeto.vendas.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
		return args -> {
			Cliente cliente = clienteRepository.save(new Cliente("robson"));
		};
	}*/
	/*@Bean
	public CommandLineRunner init(@Autowired ClienteRepository clienteRepository, @Autowired PedidoRepository pedidoRepository){
		return args -> {
			Cliente cliente = clienteRepository.save(new Cliente("robson"));
			Cliente cliente2 = clienteRepository.save(new Cliente("robson silva"));

			Pedido p = new Pedido();
			p.setCliente(cliente);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(100));

			pedidoRepository.save(p);

			Cliente c = clienteRepository.findClienteFetchPedidos(cliente.getId());
			System.out.println(c);
			System.out.println(c.getPedidos());

			pedidoRepository.findByCliente(c).forEach(System.out::println);

			Cliente c2 = clienteRepository.findClienteFetchPedidos(cliente2.getId());
			System.out.println(c2);
			System.out.println(c2.getPedidos());
		};
	}*/

	/*@Bean
	public CommandLineRunner init(@Autowired ClienteJBDCTemplate clientesjdbc, @Autowired ClienteEntiyManager clientes){
		return args -> {
			clientes.salvar(new Cliente("robson"));
			clientes.salvar(new Cliente("roberto"));

			List<Cliente> todosClientes = clientesjdbc.listar();
			todosClientes.forEach(System.out::println);
		};
	}

	@RequestMapping("/hello")
	public String hello(){
		return "Hello World";
	}*/

}
