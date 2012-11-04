package usuario;
import org.junit.Before;
import org.junit.Test;

import models.Tuit;
import models.Usuario;
import play.test.Fixtures;
import play.test.UnitTest;


public class UsuarioTest extends UnitTest {

	@Before
	public void before() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void elNickDebeSerRequerido() {
		Usuario u = new Usuario();
		assertFalse(u.validateAndCreate());
	}

	@Test
	public void elNickDebeSerUnico() {
		Usuario existe = Usuario.all().first();
		Usuario nuevo = new Usuario();
		nuevo.nick = existe.nick;
		assertFalse(nuevo.validateAndCreate());
	}

	@Test
	public void seDebePoderAniadirUnTuit() {
		Usuario u = Usuario.all().first();
		int cuenta = u.tuits.size();
		Tuit t = new Tuit();
		t.texto = "texto";
		u.addTuit(t);
		assertNotNull(t.autor);
		assertEquals(cuenta + 1, u.tuits.size());
		assertTrue(u.validateAndSave());
		u = Usuario.all().first();
		assertEquals(cuenta + 1, u.tuits.size());
	}
	
	@Test
	public void debeRecuperarLosTuits() {
		Usuario u = Usuario.all().first();
		assertEquals(1, u.tuits.size());
	}

	@Test
	public void debeCrearse() {
		Usuario nuevo = new Usuario();
		nuevo.nick = "nuevo-nick";
		assertTrue(nuevo.validateAndCreate());
	}


}
