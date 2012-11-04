package controllers;

import helper.PaginationHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.Tuit;
import models.Usuario;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

/**
 * Controlador de Tuits. Aunque es un controlador independiente (por claridad), se accede a través de URLs anidadas de usuario
 * @author jm
 *
 */
public class Tuits extends Controller {
	
	/**
	 * Método de utilidad para encontrar un tuit a partir de su id
	 * @param uid
	 * @param tid
	 * @return
	 */
	protected static Tuit findTuit(Integer uid, Integer tid) {
		Tuit t = Tuit.findById(tid);
		if (t == null) {
			response.status = 400;
			String mensaje = "Id no existente";
			renderTemplate("error." + request.format, mensaje);
		} else if (!t.autor.id.equals(uid)) {
			response.status = 400;
			String mensaje = "El tuit no corresponde con el usuario";
			renderTemplate("error." + request.format, mensaje);
		}
		return t;
	}
	
	/**
	 * Método de utilidad para crear un objeto tuit a partir de su representación JSON.
	 * Utiliza la librería Gson (incluida en play)
	 * @param json
	 * @return
	 */
	private static Tuit createTuitFromJSON(InputStream json) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("dd-MM-yyyy HH:mm:ss");
		Gson g = builder.create();
		return g.fromJson(new InputStreamReader(json), Tuit.class);
	}


	
	public static void index(Integer uid) {
		JPAQuery query = Tuit.find("byAutor_id", uid);
		List<Tuit> tuits = PaginationHelper.getPage(query, params);
		if (tuits.size() == 0) {
			notFound("No hay tuits para el usuario " + uid); // igual que respose.status = 404;
		} else {
			render(tuits);
		}
	}
	
	public static void create(Long uid) {
		Usuario u = Usuarios.findUser(uid);
		Tuit tuit = createTuitFromJSON(request.body);
		if (u == null || tuit == null) {
			badRequest();
		}

		tuit.autor = u;
		if (!tuit.validateAndCreate()) {
			response.status = 400;
			renderTemplate("error_validacion." + request.format);
		} else {
			render(tuit);
		}
	}

	public static void atributos(Integer uid, Integer tid) {
		render(findTuit(uid, tid));
	}

	public static void delete(Integer uid, Integer tid) {
		findTuit(uid, tid).delete();
	}
	
	public static void update(Integer uid, Integer tid) {
		Tuit t = findTuit(uid, tid);
		String texto = params.get("texto");
		if (texto != null) {
			t.texto = texto;
		}
		if (!t.validateAndSave()) {
			renderTemplate("error_validation");
		}		
	}

}
