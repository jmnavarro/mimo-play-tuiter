package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import net.sf.oval.guard.PreValidateThis;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Tuit extends Model {
	
	@Required
	@MaxSize(140)
	public String texto;
	
	public Date fecha;
	
	@Required
	@Basic(fetch = FetchType.LAZY)
	@ManyToOne
	public Usuario autor;
	
	@Required
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Categoria> categorias = new ArrayList<Categoria>();
	
	/**
	 * Rellena la fecha con la fecha actual
	 */
	@PrePersist
	protected void preCreate() {
		if (fecha == null) {
			fecha = new Date();
		}
	}
	
	/** 
	 * Quita los espacios sobrantes al tuit
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = (texto == null) ? null : texto.trim();
	}
	
	@Override
	public String toString() {
		return "texto=" + texto + " fecha=" + fecha + " autor=" + autor + " categorias=" + categorias.size();
	}

}
