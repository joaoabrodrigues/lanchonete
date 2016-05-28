package util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Classe utilit�ria com alguns recursos para constru��o de m�todos gen�ricos.
 *	
 */
public class Persistencia {

    /**
     * Obtem o valor da chave prim�ria de uma entidade. O m�todo procura o
     * atributo com a anota��o ID na classe e nas superclasses
     *
     * @param entidade entidade que deseja-se a chave
     * @return valor da chave
     */
    public static Object getId(Object entidade) {
        try {
            Field f = getFieldId(entidade.getClass());
            f.setAccessible(true);
            return f.get(entidade);
        } catch (Exception ex) {
            System.out.println("N�o foi poss�vel encontrar a chave prim�ria de " + entidade);
        }
        return null;
    }

    /**
     * Obt�m o valor do atributo a partir de seu nome e do objeto que o cont�m
     *
     * @param entidade
     * @param attrName
     * @return
     */
    public static Object getAttributeValue(Object entidade, String attrName) {
        try {
            //Field f = entidade.getClass().getField(attrName);
            String methodName = "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
            return entidade.getClass().getMethod(methodName, new Class[]{}).invoke(entidade, new Object[]{});
        } catch (Exception ex) {
            System.out.println("N�o foi poss�vel encontrar o atributo " + attrName + " na classe " + entidade.getClass().getName());
        }
        return null;
    }

    /**
     * Altera o valor da chave prim�ria de um objeto, procura pelo
     *
     * @Id
     * @param entidade a entidade a alterar o ID
     * @param valor novo valor
     */
    public static void setId(Object entidade, Object valor) {
        try {
            Field f = getFieldId(entidade.getClass());
            f.setAccessible(true);
            f.set(entidade, valor);
        } catch (Exception ex) {
            System.out.println("N�o foi poss�vel encontrar a chave prim�ria de " + entidade);
        }
    }

    /**
     * Obtem o atributo que � chave prim�ria de uma entidade. O m�todo procura o
     * atributo com a anota��o ID na classe e nas superclasses
     *
     * @return Field da chave
     */
    public static Field getFieldId(Class classe) {
        try {
            for (Field f : getAtributos(classe)) {
                if (f.isAnnotationPresent(Id.class)) {
                    return f;
                }
            }
        } catch (Exception ex) {
            System.out.println("N�o foi poss�vel encontrar a chave prim�ria de " + classe);
        }
        return null;
    }

    /**
     * M�todo recursivo para descobrir todos os atributos da entidade, incluindo
     * os das superclasses, se existirem
     *
     * @param classe classe da entidade
     * @return um ArrayList com os atributos (Fields) da entidade
     */
    public static List<Field> getAtributos(Class classe) {
        List<Field> lista = new ArrayList<Field>();
        if (!classe.getSuperclass().equals(Object.class)) {
            lista.addAll(getAtributos(classe.getSuperclass()));
        }
        for (Field f : classe.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            lista.add(f);
        }
        return lista;
    }

    /**
     * M�todo que retorna o primeiro atributo da classe de entidade. N�o retorna
     * atributos est�ticos. Evita retorna atributos com a anota��o
     * GeneratedValue, isto �, caso exista algum atributo n�o est�tico sem esta
     * anota��o, este ser� retornado. Este m�todo � destinado a constru��o de
     * consultas gen�ricas.
     *
     * @param classe Classe da entidade.
     * @return
     */
    public static Field primeiroAtributo(Class classe) {
        Field f = null;
        for (Field atributo : getAtributos(classe)) {
            if (Modifier.isStatic(atributo.getModifiers())) {
                continue;
            }
            if (f == null) {
                f = atributo;
            }
            if (!atributo.isAnnotationPresent(GeneratedValue.class)) {
                return atributo;
            } else if (f == null) {
                f = atributo;
            }
        }
        return f;
    }

    public static void setDataRegistro(Object entidade) {
        Class classe = entidade.getClass();
        try {
            for (Field f : getAtributos(classe)) {
                if (f.getName().equals("dataRegistro")) {
                    f.setAccessible(true);
                    f.set(entidade, new Date());
                    return;
                }
            }
        } catch (Exception ex) {
            System.out.println("N�o foi poss�vel encontrar o atributo dataRegistro na classe " + classe);
        }
    }
}
