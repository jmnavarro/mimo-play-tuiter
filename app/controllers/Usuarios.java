package controllers;

import groovy.json.JsonBuilder;
import helper.PaginationHelper;

import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

import models.Usuario;
import play.mvc.Controller;

/**
 * Controlador de usuarios
 * @author jm
 *
 */
public class Usuarios extends Controller {
	
	/**
	 * Método de utilidad para encontrar usuario a partir de su id
	 * @param uid
	 * @return
	 */
	protected static Usuario findUser(Long uid) {
		Usuario u = Usuario.findById(uid);
		if (u == null) {
			response.status = 400;
			String mensaje = "Id no existente";
			renderTemplate("error." + request.format, mensaje);
		}
		return u;
	}


	public static void index() {
		List<Usuario> usuarios = PaginationHelper.getPage(Usuario.all(), params);
		if (usuarios.size() == 0) {
			notFound("No hay usuarios"); // igual que response.status = 404;
		} else {
			render(usuarios);
		}
	}
	
	
	public static void create() {
		Usuario usuario = new Gson().fromJson(new InputStreamReader(request.body), Usuario.class);
		if (usuario == null) {
			badRequest();
		}
		
		if (!usuario.validateAndCreate()) {
			response.status = 400;
			renderTemplate("error_validacion." + request.format);
		} else {
			render(usuario);
		}
	}
	
	public static void atributos(Long uid) {
		render(findUser(uid));
	}

	public static void delete(Long uid) {
		findUser(uid).delete();
	}
	
	public static void update(Long uid) {
		Usuario u = findUser(uid);
		Usuario nuevo = new Gson().fromJson(new InputStreamReader(request.body), Usuario.class);
		if (u == null || nuevo == null) {
			badRequest();
		}
		if (!nuevo.nick.equals("")) {
			u.nick = nuevo.nick;
		}
		if (!u.validateAndSave()) {
			renderTemplate("error_validation");
		}		
	}

}
