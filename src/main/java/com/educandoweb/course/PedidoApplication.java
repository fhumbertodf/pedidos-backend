package com.educandoweb.course;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.educandoweb.course.domain.Categoria;
import com.educandoweb.course.domain.Produto;
import com.educandoweb.course.repository.CategoriaRepository;
import com.educandoweb.course.repository.ProdutoRepository;

@SpringBootApplication
public class PedidoApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;

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
	}
}