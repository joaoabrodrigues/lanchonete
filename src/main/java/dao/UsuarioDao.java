package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Usuario;

@Named
@SessionScoped
public class UsuarioDao extends GenericDao<Usuario, Long> implements Serializable {

	private static final long serialVersionUID = 2661299221348676675L;

	public UsuarioDao() {
		super(Usuario.class);
	}
	
	public List<Usuario> usuariosOrdem() {
        String hql = "from " + persistedClass.getSimpleName() + " obj  order by username";
        Query q = entityManager.createQuery(hql);
        return q.getResultList();
    }
	
	public void remover(String username) {
		Usuario entity = encontrar(username);
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		Usuario mergedEntity = entityManager.merge(entity);
		entityManager.remove(mergedEntity);
		entityManager.flush();
		tx.commit();
	}
	
	public Usuario encontrar(String username) {
		return entityManager.find(persistedClass, username);
	}
}

