package dao;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import model.Aluno;
import java.io.Serializable;

@Named
@SessionScoped
public class AlunoDao extends GenericDao<Aluno, Long> implements Serializable {

	private static final long serialVersionUID = 2661299221348676675L;

	public AlunoDao() {
		super(Aluno.class);
	}   
}

