package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Query;

import model.Pedido;

@Named
@SessionScoped
public class PedidoDao extends GenericDao<Pedido, Long> implements Serializable {

	private static final long serialVersionUID = 1156222642825446071L;

	public PedidoDao() {
		super(Pedido.class);
	}   
	
	public List<Pedido> pedidosNaoPagos() {
        String hql = "from " + persistedClass.getSimpleName() + " obj where pago = false order by aluno.nome";
        Query q = entityManager.createQuery(hql);
        return q.getResultList();
    }
	
	public List<Pedido> pedidosOrdem() {
        String hql = "from " + persistedClass.getSimpleName() + " obj  order by aluno.nome";
        Query q = entityManager.createQuery(hql);
        return q.getResultList();
    }
}

