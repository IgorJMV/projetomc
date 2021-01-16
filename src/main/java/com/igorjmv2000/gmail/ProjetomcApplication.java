package com.igorjmv2000.gmail;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.igorjmv2000.gmail.domain.Categoria;
import com.igorjmv2000.gmail.domain.Cidade;
import com.igorjmv2000.gmail.domain.Cliente;
import com.igorjmv2000.gmail.domain.Endereco;
import com.igorjmv2000.gmail.domain.Estado;
import com.igorjmv2000.gmail.domain.Pagamento;
import com.igorjmv2000.gmail.domain.PagamentoComBoleto;
import com.igorjmv2000.gmail.domain.PagamentoComCartao;
import com.igorjmv2000.gmail.domain.Pedido;
import com.igorjmv2000.gmail.domain.Produto;
import com.igorjmv2000.gmail.domain.enums.EstadoPagamento;
import com.igorjmv2000.gmail.domain.enums.TipoCliente;
import com.igorjmv2000.gmail.repositories.CategoriaRepository;
import com.igorjmv2000.gmail.repositories.CidadeRepository;
import com.igorjmv2000.gmail.repositories.ClienteRepository;
import com.igorjmv2000.gmail.repositories.EnderecoRepository;
import com.igorjmv2000.gmail.repositories.EstadoRepository;
import com.igorjmv2000.gmail.repositories.PagamentoRepository;
import com.igorjmv2000.gmail.repositories.PedidoRepository;
import com.igorjmv2000.gmail.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Categorias e Produtos
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		//Estados e Cidades
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		//Cliente e endereços
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "363.789.123-77", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("(11) 92736-3323", "(35) 99383-8393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220-834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777-012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		//Pedidos e Pagamentos
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2020 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2020 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2020 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	}

}
