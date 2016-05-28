package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import converter.ConverterGenerico;
import dao.AlunoDao;
import dao.PedidoDao;
import model.Aluno;
import model.Pedido;
import util.BooleanConverter;
import util.MoneyConverter;

@Named
@SessionScoped
public class PedidoController implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoDao pedidoDao;

	@Inject
	private AlunoDao alunoDao;
	
	private Pedido pedido;
	
	private ConverterGenerico alunoConverter;
	
	private List<Pedido> listPedido;
	
	private MoneyConverter moneyConverter;
	
	private BooleanConverter booleanConverter;
	
	public PedidoController() {
		pedido = new Pedido();
		listPedido = new ArrayList<Pedido>();
	}
	
	public ConverterGenerico getAlunoConverter() {
		return alunoConverter;
	}

	public void setAlunoConverter(ConverterGenerico alunoConverter) {
		this.alunoConverter = alunoConverter;
	}

	public List<Pedido> getListPedido() {
		return pedidoDao.getList();
	}
	
	public List<Pedido> getListPedidoOrdem() {
		return pedidoDao.pedidosOrdem();
	}
	
	public List<Pedido> getPedidosNaoPagos(){
		return pedidoDao.pedidosNaoPagos();
	}

	public void setListPedido(List<Pedido> listPedido) {
		this.listPedido = listPedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public MoneyConverter getMoneyConverter() {
        if (moneyConverter == null) {
            moneyConverter = new MoneyConverter();
        }
        return moneyConverter;
    }
	
	public BooleanConverter getBooleanConverter() {
		if(booleanConverter == null){
			booleanConverter = new BooleanConverter();
		}
		return booleanConverter;
	}

	public String cancelarIncluir(){
		return "lista_pedido";
	}
	
	public void novo(){
		pedido = new Pedido();
	}
	
	public String savePedido() {
		try {
			if (validaPreenchimentoDosCampos(pedido)) {
				pedidoDao.salvar(pedido);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Pedido salvo com sucesso."));
				return "lista_pedido";
			}
			return "pedido";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			throw e;
		} finally {
			pedido = new Pedido();
		}
		
	}
	
	private boolean validaPreenchimentoDosCampos(Pedido pedido) {
		if(pedido.getAluno() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O campo aluno é obrigatório!"));
			return false;
		}
		
		if(pedido.getValor() == null || pedido.getValor().equals(BigDecimal.ZERO)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O campo valor é obrigatório e deve ser preenchido com um valor maior que ZERO!"));
			return false;
		}
		
		return true;
	}
	
	public void editar(ActionEvent e){ 
		setPedido((Pedido) e.getComponent().getAttributes().get("pedido"));
	}
	
	public void excluir(ActionEvent e) {
		pedido = (Pedido) e.getComponent().getAttributes().get("pedido");
		pedidoDao.remover(pedido.getId());
	}
	
	public List<Aluno> completaAluno(String parte) {
		return alunoDao.listaFiltrando(parte.trim(), "nome");
	}
	
	public ConverterGenerico getConverterAluno() {
        if (alunoConverter == null) {
        	alunoConverter = new ConverterGenerico(Aluno.class, alunoDao);
        }
        return alunoConverter;
    }

}
