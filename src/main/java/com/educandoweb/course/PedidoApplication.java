package com.educandoweb.course;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.educandoweb.course.domain.Autorizacao;
import com.educandoweb.course.domain.Categoria;
import com.educandoweb.course.domain.Cidade;
import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.domain.Endereco;
import com.educandoweb.course.domain.Estado;
import com.educandoweb.course.domain.ItemPedido;
import com.educandoweb.course.domain.Pagamento;
import com.educandoweb.course.domain.PagamentoComBoleto;
import com.educandoweb.course.domain.PagamentoComCartao;
import com.educandoweb.course.domain.Pedido;
import com.educandoweb.course.domain.Produto;
import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.domain.enumeration.EstadoPagamento;
import com.educandoweb.course.domain.enumeration.Perfil;
import com.educandoweb.course.domain.enumeration.TipoCliente;
import com.educandoweb.course.repository.AutorizacaoRepository;
import com.educandoweb.course.repository.CategoriaRepository;
import com.educandoweb.course.repository.CidadeRepository;
import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.repository.EnderecoRepository;
import com.educandoweb.course.repository.EstadoRepository;
import com.educandoweb.course.repository.ItemPedidoRepository;
import com.educandoweb.course.repository.PagamentoRepository;
import com.educandoweb.course.repository.PedidoRepository;
import com.educandoweb.course.repository.ProdutoRepository;
import com.educandoweb.course.repository.UsuarioRepository;
import com.educandoweb.course.util.RandomUtil;

@SpringBootApplication
public class PedidoApplication implements CommandLineRunner {
	
	@Autowired
	AutorizacaoRepository authorityRepository;

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
	ItemPedidoRepository itemPedidoRepository;

	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	UsuarioRepository userRepository;	

	public static void main(String[] args) {
		SpringApplication.run(PedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Autorizacao aut1 = new Autorizacao();
		aut1.setName(Perfil.ADMIN.getDescricao());
		
		Autorizacao aut2 = new Autorizacao();
		aut2.setName(Perfil.ANONYMOUS.getDescricao());
		
		Autorizacao aut3 = new Autorizacao();
		aut3.setName(Perfil.USER.getDescricao());
		
		authorityRepository.saveAll(Arrays.asList(aut1,aut2,aut3));
		
		Categoria cat1 = new Categoria();
		cat1.setNome("Informática");
		Categoria cat2 = new Categoria();
		cat2.setNome("Escritório");
		Categoria cat3 = new Categoria();
		cat3.setNome("Cama mesa e banho");
		Categoria cat4 = new Categoria();
		cat4.setNome("Eletrônicos");
		Categoria cat5 = new Categoria();
		cat5.setNome("Jardinagem");
		Categoria cat6 = new Categoria();
		cat6.setNome("Decoração");
		Categoria cat7 = new Categoria();
		cat7.setNome("Perfumaria");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));

		Produto p1 = new Produto();
		p1.setNome("Computador");
		p1.setPreco(2000.00);
		Produto p2 = new Produto();
		p2.setNome("Impressora");
		p2.setPreco(800.00);
		Produto p3 = new Produto();
		p3.setNome("Mouse");
		p3.setPreco(80.00);
		Produto p4 = new Produto();
		p4.setNome("Mesa de escritório");
		p4.setPreco(300.00);
		Produto p5 = new Produto();
		p5.setNome("Toalha");
		p5.setPreco(50.00);
		Produto p6 = new Produto();
		p6.setNome("Colcha");
		p6.setPreco(200.00);
		Produto p7 = new Produto();
		p7.setNome("TV true color");
		p7.setPreco(1200.00);
		Produto p8 = new Produto();
		p8.setNome("Roçadeira");
		p8.setPreco(800.00);
		Produto p9 = new Produto();
		p9.setNome("Abajour");
		p9.setPreco(100.00);
		Produto p10 = new Produto();
		p10.setNome("Pendente");
		p10.setPreco(180.00);
		Produto p11 = new Produto();
		p11.setNome("Shampoo");
		p11.setPreco(90.00);

		Produto p12 = new Produto();
		p12.setNome("Produto 12");
		p12.setPreco(10.00);
		Produto p13 = new Produto();
		p13.setNome("Produto 13");
		p13.setPreco(10.00);
		Produto p14 = new Produto();
		p14.setNome("Produto 14");
		p14.setPreco(10.00);
		Produto p15 = new Produto();
		p15.setNome("Produto 15");
		p15.setPreco(10.00);
		Produto p16 = new Produto();
		p16.setNome("Produto 16");
		p16.setPreco(10.00);
		Produto p17 = new Produto();
		p17.setNome("Produto 17");
		p17.setPreco(10.00);
		Produto p18 = new Produto();
		p18.setNome("Produto 18");
		p18.setPreco(10.00);
		Produto p19 = new Produto();
		p19.setNome("Produto 19");
		p19.setPreco(10.00);
		Produto p20 = new Produto();
		p20.setNome("Produto 20");
		p20.setPreco(10.00);
		Produto p21 = new Produto();
		p21.setNome("Produto 21");
		p21.setPreco(10.00);
		Produto p22 = new Produto();
		p22.setNome("Produto 22");
		p22.setPreco(10.00);
		Produto p23 = new Produto();
		p23.setNome("Produto 23");
		p23.setPreco(10.00);
		Produto p24 = new Produto();
		p24.setNome("Produto 24");
		p24.setPreco(10.00);
		Produto p25 = new Produto();
		p25.setNome("Produto 25");
		p25.setPreco(10.00);
		Produto p26 = new Produto();
		p26.setNome("Produto 26");
		p26.setPreco(10.00);
		Produto p27 = new Produto();
		p27.setNome("Produto 27");
		p27.setPreco(10.00);
		Produto p28 = new Produto();
		p28.setNome("Produto 28");
		p28.setPreco(10.00);
		Produto p29 = new Produto();
		p29.setNome("Produto 29");
		p29.setPreco(10.00);
		Produto p30 = new Produto();
		p30.setNome("Produto 30");
		p30.setPreco(10.00);
		Produto p31 = new Produto();
		p31.setNome("Produto 31");
		p31.setPreco(10.00);
		Produto p32 = new Produto();
		p32.setNome("Produto 32");
		p32.setPreco(10.00);
		Produto p33 = new Produto();
		p33.setNome("Produto 33");
		p33.setPreco(10.00);
		Produto p34 = new Produto();
		p34.setNome("Produto 34");
		p34.setPreco(10.00);
		Produto p35 = new Produto();
		p35.setNome("Produto 35");
		p35.setPreco(10.00);
		Produto p36 = new Produto();
		p36.setNome("Produto 36");
		p36.setPreco(10.00);
		Produto p37 = new Produto();
		p37.setNome("Produto 37");
		p37.setPreco(10.00);
		Produto p38 = new Produto();
		p38.setNome("Produto 38");
		p38.setPreco(10.00);
		Produto p39 = new Produto();
		p39.setNome("Produto 39");
		p39.setPreco(10.00);
		Produto p40 = new Produto();
		p40.setNome("Produto 40");
		p40.setPreco(10.00);
		Produto p41 = new Produto();
		p41.setNome("Produto 41");
		p41.setPreco(10.00);
		Produto p42 = new Produto();
		p42.setNome("Produto 42");
		p42.setPreco(10.00);
		Produto p43 = new Produto();
		p43.setNome("Produto 43");
		p43.setPreco(10.00);
		Produto p44 = new Produto();
		p44.setNome("Produto 44");
		p44.setPreco(10.00);
		Produto p45 = new Produto();
		p45.setNome("Produto 45");
		p45.setPreco(10.00);
		Produto p46 = new Produto();
		p46.setNome("Produto 46");
		p46.setPreco(10.00);
		Produto p47 = new Produto();
		p47.setNome("Produto 47");
		p47.setPreco(10.00);
		Produto p48 = new Produto();
		p48.setNome("Produto 48");
		p48.setPreco(10.00);
		Produto p49 = new Produto();
		p49.setNome("Produto 49");
		p49.setPreco(10.00);
		Produto p50 = new Produto();
		p50.setNome("Produto 50");
		p50.setPreco(10.00);

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		p12.getCategorias().add(cat1);
		p13.getCategorias().add(cat1);
		p14.getCategorias().add(cat1);
		p15.getCategorias().add(cat1);
		p16.getCategorias().add(cat1);
		p17.getCategorias().add(cat1);
		p18.getCategorias().add(cat1);
		p19.getCategorias().add(cat1);
		p20.getCategorias().add(cat1);
		p21.getCategorias().add(cat1);
		p22.getCategorias().add(cat1);
		p23.getCategorias().add(cat1);
		p24.getCategorias().add(cat1);
		p25.getCategorias().add(cat1);
		p26.getCategorias().add(cat1);
		p27.getCategorias().add(cat1);
		p28.getCategorias().add(cat1);
		p29.getCategorias().add(cat1);
		p30.getCategorias().add(cat1);
		p31.getCategorias().add(cat1);
		p32.getCategorias().add(cat1);
		p33.getCategorias().add(cat1);
		p34.getCategorias().add(cat1);
		p35.getCategorias().add(cat1);
		p36.getCategorias().add(cat1);
		p37.getCategorias().add(cat1);
		p38.getCategorias().add(cat1);
		p39.getCategorias().add(cat1);
		p40.getCategorias().add(cat1);
		p41.getCategorias().add(cat1);
		p42.getCategorias().add(cat1);
		p43.getCategorias().add(cat1);
		p44.getCategorias().add(cat1);
		p45.getCategorias().add(cat1);
		p46.getCategorias().add(cat1);
		p47.getCategorias().add(cat1);
		p48.getCategorias().add(cat1);
		p49.getCategorias().add(cat1);
		p50.getCategorias().add(cat1);	

		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16,
				p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p33, p34, p35, p36, p37,
				p38, p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));

		Estado est1 = new Estado();
		est1.setNome("Minas Gerais");

		Estado est2 = new Estado();
		est2.setNome("São Paulo");

		Cidade c1 = new Cidade();
		c1.setNome("Uberlândia");
		c1.setEstado(est1);

		Cidade c2 = new Cidade();
		c2.setNome("São Paulo");
		c2.setEstado(est2);

		Cidade c3 = new Cidade();
		c3.setNome("Campinas");
		c3.setEstado(est2);

		est1.setCidades(new HashSet<Cidade>(Arrays.asList(c1)));
		est2.setCidades(new HashSet<Cidade>(Arrays.asList(c2, c3)));

		estadoRepository.saveAll(new HashSet<Estado>(Arrays.asList(est1, est2)));
		cidadeRepository.saveAll(new HashSet<Cidade>(Arrays.asList(c1, c2, c3)));
		
		Usuario user1 = new Usuario();
        user1.setLogin("maria@gmail.com");
        user1.setPassword(passwordEncoder.encode("1q2w3e4r"));
        user1.setActivated(false);
        user1.setActivationKey(RandomUtil.generateActivationKey());
        user1.setAuthorities(new HashSet<Autorizacao>(Arrays.asList(aut3)));
                
        Cliente cli1 = new Cliente();
		cli1.setNome("Maria Silva");
		cli1.setEmail("maria@gmail.com");
		cli1.setCpfOuCnpj("36378912377");
		cli1.setTipoCliente(TipoCliente.PESSOAFISICA);
		cli1.setUser(user1);
		
		cli1.setTelefones(new HashSet<String>(Arrays.asList("27363323", "93838393")));
		
		Usuario user2 = new Usuario();
        user2.setLogin("nelio.iftm@gmail.com");
        user2.setPassword(passwordEncoder.encode("1q2w3e4r"));
        user2.setActivated(false);
        user2.setActivationKey(RandomUtil.generateActivationKey());
        user2.setAuthorities(new HashSet<Autorizacao>(Arrays.asList(aut3)));
		
		Cliente cli2 = new Cliente();
		cli2.setNome("Ana Costa");
		cli2.setEmail("nelio.iftm@gmail.com");
		cli2.setCpfOuCnpj("31628382740");
		cli2.setTipoCliente(TipoCliente.PESSOAFISICA);	
		cli2.setUser(user2);		
		
		cli2.setTelefones(new HashSet<String>(Arrays.asList("93883321", "34252625")));	
		
		user1.setCliente(cli1);
		user2.setCliente(cli2);
        
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
				
		Endereco e3 = new Endereco();
		e3.setLogradouro("Avenida Floriano");
		e3.setNumero("2106");
		e3.setBairro("Centro");
		e3.setCep("281777012");
		e3.setCidade(c2);
		e3.setCliente(cli2);
		
		cli1.setEnderecos(new HashSet<Endereco>(Arrays.asList(e1, e2)));
		cli2.setEnderecos(new HashSet<Endereco>(Arrays.asList(e3)));

		clienteRepository.saveAll(new HashSet<Cliente>(Arrays.asList(cli1, cli2))); 

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido();
		ped1.setInstante(sdf.parse("30/09/2017 10:32"));
		ped1.setCliente(cli1);
		ped1.setEnderecoDeEntrega(e1);

		Pedido ped2 = new Pedido();
		ped2.setInstante(sdf.parse("10/10/2017 19:35"));
		ped2.setCliente(cli1);
		ped2.setEnderecoDeEntrega(e2);

		PagamentoComCartao pagto1 = new PagamentoComCartao();
		pagto1.setEstadoPagamento(EstadoPagamento.QUITADO);
		pagto1.setNumeroDeParcelas(6);
		pagto1.setPedido(ped1);

		ped1.setPagamento(pagto1);

		PagamentoComBoleto pagto2 = new PagamentoComBoleto();
		pagto2.setEstadoPagamento(EstadoPagamento.PENDENTE);
		pagto2.setDataVencimento(sdf.parse("20/10/2017 00:00"));
		pagto2.setDataPagamento(null);
		pagto2.setPedido(ped2);

		ped2.setPagamento(pagto2);

		pedidoRepository.saveAll(new HashSet<Pedido>(Arrays.asList(ped1, ped2)));
		pagamentoRepository.saveAll(new HashSet<Pagamento>(Arrays.asList(pagto1, pagto2)));

		ItemPedido ip1 = new ItemPedido();
		ip1.setDesconto(0.00);
		ip1.setQuantidade(1);
		ip1.setPreco(2000.00);
		ip1.setProduto(p1);
		ip1.setPedido(ped1);

		ItemPedido ip2 = new ItemPedido();
		ip2.setDesconto(0.00);
		ip2.setQuantidade(2);
		ip2.setPreco(80.00);
		ip2.setProduto(p3);
		ip2.setPedido(ped1);

		ItemPedido ip3 = new ItemPedido();
		ip3.setDesconto(100.00);
		ip3.setQuantidade(1);
		ip3.setPreco(800.00);
		ip3.setProduto(p2);
		ip3.setPedido(ped2);

		itemPedidoRepository.saveAll(new HashSet<ItemPedido>(Arrays.asList(ip1, ip2, ip3)));
	}
}