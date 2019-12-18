package com.educandoweb.course;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.educandoweb.course.domain.Categoria;
import com.educandoweb.course.domain.Cidade;
import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.domain.Endereco;
import com.educandoweb.course.domain.Estado;
import com.educandoweb.course.domain.Produto;
import com.educandoweb.course.domain.enumeration.TipoCliente;
import com.educandoweb.course.repository.CategoriaRepository;
import com.educandoweb.course.repository.CidadeRepository;
import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.repository.EnderecoRepository;
import com.educandoweb.course.repository.EstadoRepository;
import com.educandoweb.course.repository.ProdutoRepository;

@SpringBootApplication
public class PedidoApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(PedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria();
		cat1.setNome("Informática");

		Categoria cat2 = new Categoria();
		cat2.setNome("Escritório");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));

		Produto p1 = new Produto();
		p1.setNome("Computador");
		p1.setPreco(2000.00);

		Produto p2 = new Produto();
		p2.setNome("Impressora");
		p2.setPreco(800.00);

		Produto p3 = new Produto();
		p3.setNome("Mouse");
		p3.setPreco(80.00);

		p1.setCategorias(new HashSet<Categoria>(Arrays.asList(cat1)));
		p2.setCategorias(new HashSet<Categoria>(Arrays.asList(cat1,cat2)));
		p3.setCategorias(new HashSet<Categoria>(Arrays.asList(cat1)));

		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado();
		est1.setNome("Minas Gerais");
		
		Estado est2 = new Estado();
		est2.setNome("São Paulo");
		
		Cidade c1 = new Cidade();
		c1.setNome("Uberlândia");
		c1.estado(est1);
		
		Cidade c2 = new Cidade();
		c2.setNome("São Paulo");
		c2.estado(est2);
		
		Cidade c3 = new Cidade();
		c3.setNome("Campinas");
		c3.estado(est2);
		
		est1.setCidades(new HashSet<Cidade>(Arrays.asList(c1)));
		est2.setCidades(new HashSet<Cidade>(Arrays.asList(c2,c3)));
		
		estadoRepository.saveAll(new HashSet<Estado>(Arrays.asList(est1,est2)));
		cidadeRepository.saveAll(new HashSet<Cidade>(Arrays.asList(c1,c2,c3)));	
		
		Cliente cli1 = new Cliente();
		cli1.setNome("Maria Silva");
		cli1.setEmail("maria@gmail.com");
		cli1.setCpfOuCnpj("36378912377");
		cli1.setTipoCliente(TipoCliente.PESSOAFISICA);
		
		cli1.setTelefones(new HashSet<String>(Arrays.asList("27363323","93838393")));
		
		clienteRepository.saveAll(new HashSet<Cliente>(Arrays.asList(cli1)));
		
		Endereco e1 = new Endereco();
		e1.setLogradouro("Rua Flores");
		e1.setNumero("300");
		e1.setComplemento("Apto 203");
		e1.setBairro("Jardim");
		e1.setCep("38220834");
		e1.setCidade(c1);
		e1.setCliente(cli1);
		
		Endereco e2 = new Endereco();
		e2.setLogradouro("Avenida Matos");
		e2.setNumero("105");
		e2.setComplemento("Sala 800");
		e2.setBairro("Cantro");
		e2.setCep("38777012");
		e2.setCidade(c2);
		e2.setCliente(cli1);		
		
		enderecoRepository.saveAll(new HashSet<Endereco>(Arrays.asList(e1,e2)));
	}
}