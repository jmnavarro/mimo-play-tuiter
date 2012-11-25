package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Categoria extends Model {
	@Required
	public String nombre;
	
	@ManyToMany(mappedBy = "categorias")
	public List<Tuit> tuits = new ArrayList<Tuit>();
	
	public String toString() {
		return "categoria=" + nombre + " tuits=" + tuits.size();
	}
}
