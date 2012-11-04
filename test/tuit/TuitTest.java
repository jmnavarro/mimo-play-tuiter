package tuit;
import org.junit.Before;
import org.junit.Test;

import models.Tuit;
import models.Usuario;
import play.data.validation.Validation;
import play.test.Fixtures;
import play.test.UnitTest;


public class TuitTest extends UnitTest {
	
	@Before
	public void before() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
	}
	
	@Test
	public void elTextoDebeSerRequerido() {
		Usuario u = Usuario.all().first();
		Tuit t = new Tuit();
		t.autor = u;
		assertFalse(t.validateAndCreate());
	}

	@Test
	public void elTextoDebeSerComoMaximoDe140Caracteres() {
		Usuario u = Usuario.all().first();
		Tuit t = new Tuit();
		t.autor = u;
		t.texto = String.format("%150s", "texto").replace(" ", "*");
		assertFalse(t.validateAndCreate());
	}

	@Test
	public void elTextoDebeQuitarLosEspaciosAlPrincipioYFinal() {
		Usuario u = Usuario.all().first();
		Tuit t = new Tuit();
		t.autor = u;
		t.texto = String.format("%150s", "texto");
		assertEquals("texto", t.texto);
		assertTrue(t.validateAndCreate());
	}

	@Test
	public void elAutorDebeSerRequerido() {
		Tuit t = new Tuit();
		t.texto = "texto";
		assertFalse(t.validateAndCreate());
	}

	@Test
	public void laFechaSeDebeRellenarSola() {
		Usuario u = Usuario.all().first();
		Tuit t = new Tuit();
		t.autor = u;
		t.texto = "texto";
		assertTrue(t.validateAndCreate());
		assertNotNull(t.fecha);
	}


}
