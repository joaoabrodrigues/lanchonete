package dao;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Query;

import model.Aluno;
import model.Pedido;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AlunoDao extends GenericDao<Aluno, Long> implements Serializable {

	private static final long serialVersionUID = 2661299221348676675L;

	public AlunoDao() {
		super(Aluno.class);
	}
	
	public List<Aluno> alunosOrdem() {
        String hql = "from " + persistedClass.getSimpleName() + " obj  order by nome";
        Query q = entityManager.createQuery(hql);
        return q.getResultList();
    }
}

