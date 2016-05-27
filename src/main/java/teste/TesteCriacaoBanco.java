package teste;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Aluno;
import model.Pedido;

public class TesteCriacaoBanco {
	
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("lanchonete_jsf");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
		Pedido pedido = new Pedido();
		Pedido pedido2 = new Pedido();
		Aluno aluno = new Aluno();
		
		aluno.setNome("João");
		aluno.setSerie("1e");
		aluno.setTelefone("(44) 9943-5293");
		
		pedido.setValor(BigDecimal.TEN);
		pedido.setAluno(aluno);
		
		pedido2.setValor(BigDecimal.ONE);
		pedido2.setAluno(aluno);
		
		List<Pedido> pedidos = new ArrayList<Pedido>();
		pedidos.add(pedido);
		pedidos.add(pedido2);
		
		aluno.setPedidos(pedidos);
		
		manager.persist(aluno);
		trx.commit();
		
	}

}
