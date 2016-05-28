package converter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import dao.GenericDao;
import util.Persistencia;

public class ConverterGenerico implements Converter, Serializable {

	private static final long serialVersionUID = -6207159064725619765L;
	private GenericDao dao;
	private Class classe;
	
	public ConverterGenerico(Class classe, GenericDao d) {
        this.dao = d;
        this.classe = classe;
    }
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Object chave = Persistencia.getFieldId(classe).getType().getConstructor(String.class).newInstance(value);
            return dao.recuperar(classe, chave);
        }  catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossível continuar", "Erro ao converter: " + value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(Persistencia.getId(value));
        } else {
            return null;
        }
    }
}
