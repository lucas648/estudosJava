package com.lucascosta.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
//habilita que a classe execute alguma ação quando a classe iniciar
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucascosta.cursomc.domain.Categoria;
import com.lucascosta.cursomc.domain.Cidade;
import com.lucascosta.cursomc.domain.Cliente;
import com.lucascosta.cursomc.domain.Endereco;
import com.lucascosta.cursomc.domain.Estado;
import com.lucascosta.cursomc.domain.ItemPedido;
import com.lucascosta.cursomc.domain.Pagamento;
import com.lucascosta.cursomc.domain.PagamentoComBoleto;
import com.lucascosta.cursomc.domain.PagamentoComCartao;
import com.lucascosta.cursomc.domain.Pedido;
import com.lucascosta.cursomc.domain.Produto;
import com.lucascosta.cursomc.domain.enums.EstadoPagamento;
import com.lucascosta.cursomc.domain.enums.TipoCliente;
import com.lucascosta.cursomc.repositories.CategoriaRepository;
import com.lucascosta.cursomc.repositories.CidadeRepository;
import com.lucascosta.cursomc.repositories.ClienteRepository;
import com.lucascosta.cursomc.repositories.EnderecoRepository;
import com.lucascosta.cursomc.repositories.EstadoRepository;
import com.lucascosta.cursomc.repositories.ItemPedidoRepository;
import com.lucascosta.cursomc.repositories.PagamentoRepository;
import com.lucascosta.cursomc.repositories.PedidoRepository;
import com.lucascosta.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	/*
	 * é necessário uma instância do repository para se fazer a ligação com o BD
	 */
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
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	/*
	 * é necessário que esse método seja criado e aqui é passada a ação a ser executada ao iniciar
	 */
	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		/*
		 * faz a associação entre as categorias e seus respectivos produtos
		 */
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		/*
		 * faz a associação entre os produtos e suas respectivas categorias
		 */
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		/*
		 * através do repository passa uma lista de objetos, criada acima, para o BD
		 */
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
      	produtoRepository.saveAll(Arrays.asList(p1,p2,p3));	
      	
      	Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		/*
		 * como a relação de estado para cidade é de um para muitos, ou seja
		 * um estado pode ter várias cidades, os estados devem ser salvos primieros no BD
		 */
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2,cid3));
		
		Cliente cli1 = new Cliente(null, "Maria Dos Anjos", "Maria.anjos@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
      	cli1.getTelefones().addAll(Arrays.asList("35648975","985625478"));
      	
      	Endereco e1 = new Endereco(null, "Rua das Margaridas", "154","rua da padaria", "Jardim Das Pedras", "15426599", cli1, cid1);
      	Endereco e2 = new Endereco(null, "Av Presidente Vargas", "784"," ", "Vila Campestre", "1540600", cli1, cid2);

      	cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
      	
      	clienteRepository.saveAll(Arrays.asList(cli1));
      	enderecoRepository.saveAll(Arrays.asList(e1,e2));
      	
      	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      	
      	Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 15:25"), cli1, e1);
      	Pedido ped2 = new Pedido(null, sdf.parse("10/08/2019 14:36"), cli1, e2);
      	
      	Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
      	ped1.setPagamento(pagto1);
      	
      	Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("10/09/2019 14:41"), sdf.parse("10/09/2019 10:20"));
      	ped2.setPagamento(pagto2);
      	
      	cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
      	
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
      	pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));	
      	
      	ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
      	ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
      	ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
      	
      	ped1.getItens().addAll(Arrays.asList(ip1,ip2));
      	ped2.getItens().addAll(Arrays.asList(ip3));
      	
      	p1.getItens().addAll(Arrays.asList(ip1));
      	p2.getItens().addAll(Arrays.asList(ip3));
      	p3.getItens().addAll(Arrays.asList(ip2));
      	
      	itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
