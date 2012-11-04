package usuario;

import org.junit.Before;
import org.junit.Test;

import play.Logger;
import play.mvc.Http.Response;
import play.test.Fixtures;
import mimo.MIMOFunctionalTest;
import models.Usuario;

public class UsuariosCreateTest extends MIMOFunctionalTest {
	
	@Before
	public void before() {
		Fixtures.deleteAllModels();
	}

	@Test
    public void debeRetornarOK() {		
        Response response = POST(newRequest("json"),     // request configurada para que la respuesta sea JSON 
        							"/usuario",             // url
        							"application/json",     // content type del contenido de la petición
        							"{\"nick\":\"pepe\"}"); // contenido de la petición
        assertIsOk(response);
    }

	@Test
    public void debeRetornarContentTypeParaJSON() {		
        Response response = POST(newRequest("json"), "/usuario", "application/json", "{\"nick\":\"pepe\"}");
        assertContentType("application/json", response);
    }

	@Test
    public void debeRetornarContentTypeParaXML() {		
        Response response = POST(newRequest("xml"), "/usuario", "application/json", "{\"nick\":\"pepe\"}");
        assertContentType("text/xml", response);
    }

	@Test
    public void debeCrearse() {		
        POST(newRequest("json"), "/usuario", "application/json", "{\"nick\":\"pepe\"}");
        Usuario creado = Usuario.all().first();
        assertNotNull(creado);
        assertEquals("pepe", creado.nick);
    }
	
	@Test
    public void debeRetornarBadRequestSinJSON() {		
        Response response = POST(newRequest("json"), "/usuario", "application/json", "");
        assertStatus(400, response);
    }
	
	@Test
    public void debeRetornarErrorSiUsuarioInvalido() {		
        Response response = POST(newRequest("json"), "/usuario", "application/json", "{\"nick\":\"\"}");
        assertStatus(400, response);
    }
	
	@Test
    public void debeRetornarJSONErrorSiUsuarioInvalido() {		
        Response response = POST(newRequest("json"), "/usuario", "application/json", "{\"nick\":\"\"}");
        assertContentMatch("\"atributo\" : \"usuario.nick\"", response);
        assertContentMatch("\"mensaje\" : \"Required\"", response);
    }
	
	@Test
    public void debeRetornarXMLErrorSiUsuarioInvalido() {		
        Response response = POST(newRequest("xml"), "/usuario", "application/json", "{\"nick\":\"\"}");
        assertContentMatch("<atributo>usuario.nick</atributo>", response);
        assertContentMatch("<mensaje>Required</mensaje>", response);
    }

}
