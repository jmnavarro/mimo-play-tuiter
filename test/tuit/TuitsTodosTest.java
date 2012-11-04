package tuit;

import mimo.MIMOFunctionalTest;
import models.Usuario;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;

public class TuitsTodosTest extends MIMOFunctionalTest {

	private Usuario fixtureUser;

	@Before
	public void before() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
		
		fixtureUser = Usuario.find("byNick", "fixture-nick1").first();
		assertNotNull(fixtureUser);
	}
	
	@Test
	public void debeExistirParaJSON() {
		Response response = GET(newRequest("json"), "/usuario/" + fixtureUser.id + "/tuit/todos");
		assertIsOk(response);
	}

	@Test
	public void debeExistirParaXML() {
		Response response = GET(newRequest("xml"), "/usuario/" + fixtureUser.id + "/tuit/todos");
		assertIsOk(response);
	}

}