package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class PedidoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String cancelar(){
		return "index";
	}

}
