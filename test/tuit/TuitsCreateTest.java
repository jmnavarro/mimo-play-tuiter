package tuit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import play.Logger;
import play.mvc.Http.Response;
import play.test.Fixtures;
import mimo.MIMOFunctionalTest;
import models.Tuit;
import models.Usuario;

public class TuitsCreateTest extends MIMOFunctionalTest {
	
	private Usuario fixtureUser;
	
	private static final String JSON_TUIT = "{\"texto\":\"texto del tuit\", \"fecha\":\"01-02-2003 13:00:00\"}"; 

	@Before
	public void before() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
		
		fixtureUser = Usuario.find("byNick", "fixture-nick1").first();
		assertNotNull(fixtureUser);
	}

	@Test
    public void debeRetornarOK() {		
        Response response = POST(newRequest("json"),     // request configurada para que la respuesta sea JSON 
        							"/usuario/" + fixtureUser.id + "/tuit", // url
        							"application/json",     // content type del contenido de la petición
        							JSON_TUIT); // contenido de la petición
        assertIsOk(response);
    }

	@Test
    public void debeRetornarContentTypeParaJSON() {		
        Response response = POST(newRequest("json"), "/usuario/" + fixtureUser.id + "/tuit", "application/json", JSON_TUIT);
        assertContentType("application/json", response);
    }

	@Test
    public void debeRetornarContentTypeParaXML() {		
        Response response = POST(newRequest("xml"), "/usuario/" + fixtureUser.id + "/tuit", "application/json", JSON_TUIT);
        assertContentType("text/xml", response);
    }
	
	private Date getDateFromString(String str) {
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
			return formatoDeFecha.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Test
    public void debeCrearse() {
		Fixtures.delete(Tuit.class);
        POST(newRequest("json"), "/usuario/" + fixtureUser.id + "/tuit", "application/json", JSON_TUIT);
        Tuit creado = Tuit.all().first();
        assertNotNull(creado);
        assertEquals("texto del tuit", creado.texto);
        assertEquals(fixtureUser.id, creado.autor.id);
		assertEquals(getDateFromString("01-02-2003 13:00:00"), creado.fecha);
    }
	

}
