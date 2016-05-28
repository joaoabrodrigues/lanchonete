package dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDao<T, I extends Serializable> {

	   protected EntityManager entityManager;
	   protected EntityManagerFactory factory;
	   private Class<T> persistedClass;

	   protected GenericDao() {
	   }

	   protected GenericDao(Class<T> persistedClass) {	       
		   this();
		   this.persistedClass = persistedClass;
	       factory = Persistence.createEntityManagerFactory("lanchonete_jsf");
	       entityManager = factory.createEntityManager();
	   }

	   public T salvar(T entity) {
	       EntityTransaction t = entityManager.getTransaction();
	       t.begin();
	       entityManager.persist(entity);
	       entityManager.flush();
	       t.commit();
	       return entity;
	   }

	   public T atualizar(T entity) {
	       EntityTransaction t = entityManager.getTransaction();
	       t.begin();
	       entityManager.merge(entity);
	       entityManager.flush();
	       t.commit();
	       return entity;
	   }

	   public void remover(I id) {
	       T entity = encontrar(id);
	       EntityTransaction tx = entityManager.getTransaction();
	       tx.begin();
	       T mergedEntity = entityManager.merge(entity);
	       entityManager.remove(mergedEntity);
	       entityManager.flush();
	       tx.commit();
	   }

	   public List<T> getList() {
	       CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	       CriteriaQuery<T> query = builder.createQuery(persistedClass);
	       query.from(persistedClass);
	       return entityManager.createQuery(query).getResultList();
	   }

	   public T encontrar(I id) {
	       return entityManager.find(persistedClass, id);
	   }
}