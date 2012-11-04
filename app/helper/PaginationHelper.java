package helper;

import java.util.List;

import models.Usuario;

import play.mvc.Scope;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;

/**
 * Clase con métodos de ayuda para realizar la paginación de resultados.
 * @author JM
 *
 * @param <T>
 */
public class PaginationHelper<T extends Model> {
	
	/**
	 * Devuelve la lista de objetos de modelo para la query y parámetros pasados
	 * @param query
	 * @param params
	 * @return
	 */
	public static <T> List<T> getPage(JPAQuery query, Scope.Params params) {
		String pag = params.get("pag");
		int pageNum = (pag == null) ? 0 : Integer.valueOf(pag).intValue();

		String size = params.get("size");
		int pageSize = (size == null) ? 10 : Integer.valueOf(size).intValue();
		if (pageSize > 20) {
			pageSize = 20; // para no agotar la memoria del servidor por una petición "malintencionada"
		}

		return query.from(pageNum * pageSize).fetch(pageSize);
	}

}
