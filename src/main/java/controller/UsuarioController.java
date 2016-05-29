package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import dao.UsuarioDao;
import model.Usuario;

@Named
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	private List<Usuario> listUsuario  = new ArrayList<Usuario>();;
	 
    public UsuarioController() {
        usuario = new Usuario();
//        SecurityContext context = SecurityContextHolder.getContext();
//        if (context instanceof SecurityContext){
//            Authentication authentication = context.getAuthentication();
//            if (authentication instanceof Authentication){
//                usuario.setUsername(((User)authentication.getPrincipal()).getUsername());
//            }
//        }
    }
 
    public Usuario getUsuario() {
        return usuario;
    }
 
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Usuario> getListUsuario() {
		return usuarioDao.getList();
	}
	
	public List<Usuario> getListUsuarioOrdem() {
		return usuarioDao.usuariosOrdem();
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public String cancelarIncluir(){
		return "lista_usuario";
	}
	
	public void novo() {
		usuario = new Usuario();
	}
	
	public String saveUsuario() {
		try {
			if(validaPreenchimentoDosCampos(usuario)){
				usuarioDao.salvar(usuario);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Usuário salvo com sucesso."));
				return "lista_usuario";
			}
			return "usuario";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			throw e;
		} finally {
			usuario = new Usuario();
		}
		
	}
	
	private boolean validaPreenchimentoDosCampos(Usuario usuario) {
		if(usuario.getUsername() == null || usuario.getUsername().trim().equals("")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O campo usuário é obrigatório!"));
			return false;
		}
		
		if(usuario.getPassword() == null || usuario.getPassword().trim().equals("")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O campo senha é obrigatório!"));
			return false;
		}
		
		return true;
	}
	
	public List<Usuario> findAll() {
		return usuarioDao.getList();
	}
	
	public void editar(ActionEvent e) { 
		setUsuario((Usuario) e.getComponent().getAttributes().get("usuario"));
	}
	
	public void excluir(ActionEvent e) {
		usuario = (Usuario) e.getComponent().getAttributes().get("usuario");
		usuarioDao.remover(usuario.getUsername());
	}

}
