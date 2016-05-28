package util;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "booleanConverter")
public class BooleanConverter implements Converter, Serializable {

	private static final long serialVersionUID = -2365980747745421528L;

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String str) throws ConverterException {
        if (str == null || str.toString().trim().equalsIgnoreCase("sim")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) throws ConverterException {
        if (obj == null || obj.toString().trim().equalsIgnoreCase("true")) {
            return "Sim";
        } else {
            return "Não";
        }
    }
}
