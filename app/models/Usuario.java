package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model {
	
	@Required
	@Unique
	public String nick;

	@Basic(fetch = FetchType.LAZY)
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	public List<Tuit> tuits = new ArrayList<Tuit>();
	
	/**
	 * Añade un tuit, manteniendo la relación bidireccional
	 * @param t
	 */
	public void addTuit(Tuit t) {
		tuits.add(t);
		t.autor = this;
	}

}
