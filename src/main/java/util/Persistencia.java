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
 * Classe utilitária com alguns recursos para construção de métodos genéricos.
 *	
 */
public class Persistencia {

    /**
     * Obtem o valor da chave primária de uma entidade. O método procura o
     * atributo com a anotação ID na classe e nas superclasses
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
            System.out.println("Não foi possível encontrar a chave primária de " + entidade);
        }
        return null;
    }

    /**
     * Obtém o valor do atributo a partir de seu nome e do objeto que o contém
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
            System.out.println("Não foi possível encontrar o atributo " + attrName + " na classe " + entidade.getClass().getName());
        }
        return null;
    }

    /**
     * Altera o valor da chave primária de um objeto, procura pelo
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
            System.out.println("Não foi possível encontrar a chave primária de " + entidade);
        }
    }

    /**
     * Obtem o atributo que é chave primária de uma entidade. O método procura o
     * atributo com a anotação ID na classe e nas superclasses
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
            System.out.println("Não foi possível encontrar a chave primária de " + classe);
        }
        return null;
    }

    /**
     * Método recursivo para descobrir todos os atributos da entidade, incluindo
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
     * Método que retorna o primeiro atributo da classe de entidade. Não retorna
     * atributos estáticos. Evita retorna atributos com a anotação
     * GeneratedValue, isto é, caso exista algum atributo não estático sem esta
     * anotação, este será retornado. Este método é destinado a construção de
     * consultas genéricas.
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
            System.out.println("Não foi possível encontrar o atributo dataRegistro na classe " + classe);
        }
    }
}
