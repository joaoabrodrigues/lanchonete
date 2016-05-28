package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlunoDao;
import model.Aluno;

@Named
@SessionScoped
public class AlunoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlunoDao alunoDao;
	
	private Aluno aluno;
	
	private List<Aluno> listAluno;
	
	public AlunoController() {
		aluno = new Aluno();
		listAluno = new ArrayList<Aluno>();
	}
	
	public List<Aluno> getListAluno() {
		return alunoDao.getList();
	}

	public void setListAluno(List<Aluno> listAluno) {
		this.listAluno = listAluno;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String cancelarIncluir(){
		return "lista_aluno";
	}
	
	public void novo(){
		aluno = new Aluno();
	}
	
	public String saveAluno() {
		try {
			if(validaPreenchimentoDosCampos(aluno)){
				alunoDao.salvar(aluno);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Aluno salvo com sucesso."));
				return "lista_aluno";
			}
			return "aluno";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			throw e;
		} finally {
			aluno = new Aluno();
		}
		
	}
	
	private boolean validaPreenchimentoDosCampos(Aluno aluno) {
		if(aluno.getNome() == null || aluno.getNome().trim().equals("")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O campo nome é obrigatório!"));
			return false;
		}
		
		if(aluno.getTelefone() == null || aluno.getTelefone().trim().equals("")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O campo telefone é obrigatório!"));
			return false;
		}
		
		return true;
	}
	
	public List<Aluno> findAll() {
		return alunoDao.getList();
	}
	
	public void editar(ActionEvent e){ 
		setAluno((Aluno) e.getComponent().getAttributes().get("aluno"));
	}
	
	public void excluir(ActionEvent e) {
		aluno = (Aluno) e.getComponent().getAttributes().get("aluno");
		alunoDao.remover(aluno.getId());
	}
}
