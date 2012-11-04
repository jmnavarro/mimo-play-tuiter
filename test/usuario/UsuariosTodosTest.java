package usuario;
import mimo.MIMOFunctionalTest;
import models.Usuario;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;

public class UsuariosTodosTest extends MIMOFunctionalTest {

	@Before
	public void before() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
	}

	@Test
    public void debeExistirParaJSON() {
        Response response = GET(newRequest("json"), "/usuario/todos");
        assertIsOk(response);
    }
    
	@Test
    public void debeExistirParaXML() {
        Response response = GET(newRequest("xml"), "/usuario/todos");
        assertIsOk(response);
    }

	@Test
    public void debeRetornarNotFoundSiNoHayUsuarios() {
		Fixtures.deleteAllModels();
        Response response = GET(newRequest("json"), "/usuario/todos");
        assertStatus(404, response);
    }

	@Test
    public void debeRetornarTipoJSON() {
        Response response = GET(newRequest("json"), "/usuario/todos");
        assertContentType("application/json", response);
    }
    
	@Test
    public void debeRetornarTipoXML() {
        Response response = GET(newRequest("xml"), "/usuario/todos");
        assertContentType("text/xml", response);
    }

    @Test
    public void contenidoDebeSerCorrectoParaJSON() {
    		Usuario fixUser = Usuario.all().first();
        Response response = GET(newRequest("json"), "/usuario/todos");

        assertContentMatch("\"total\" : 1", response);
        assertContentMatch("\"id\" : " + fixUser.id, response);
        assertContentMatch("\"nick\" : \"" + fixUser.nick + '"', response);
    }

    @Test
    public void contenidoDebeSerCorrectoParaXML() {
		Usuario fixUser = Usuario.all().first();
        Response response = GET(newRequest("xml"), "/usuario/todos");
        
        assertContentMatch("<total>1</total>", response);
        assertContentMatch("<id>" + fixUser.id + "</id>", response);
        assertContentMatch("<nick>" + fixUser.nick + "</nick>", response);
    }
    
    
    @Test
    public void contenidoDebeSerCorrectoParaJSONPaginado() {
    		for (int i = 0; i < 5; i++) {
    			Usuario nuevo = new Usuario();
    			nuevo.nick = "nuevo paginado " + i;
    			assertTrue(nuevo.validateAndCreate());
    		}
    		
    		Response response = GET(newRequest("json"), "/usuario/todos?pag=1&size=2");

        assertContentMatch("\"total\" : 2", response);
        
        assertContentMatch("\"nick\" : \"nuevo paginado 1\"", response);
        assertContentMatch("\"nick\" : \"nuevo paginado 2\"", response);

        assertContentNotMatch("\"nick\" : \"nuevo paginado 0\"", response);
        assertContentNotMatch("\"nick\" : \"nuevo paginado 3\"", response);
    }
    
    
    @Test
    public void contenidoDebeSerCorrectoParaXMLPaginado() {
    		for (int i = 0; i < 5; i++) {
    			Usuario nuevo = new Usuario();
    			nuevo.nick = "nuevo paginado " + i;
    			assertTrue(nuevo.validateAndCreate());
    		}
    		
    		Response response = GET(newRequest("xml"), "/usuario/todos?pag=1&size=2");

        assertContentMatch("<total>2</total>", response);
        
        assertContentMatch("<nick>nuevo paginado 1</nick>", response);
        assertContentMatch("<nick>nuevo paginado 2</nick>", response);

        assertContentNotMatch("<nick>nuevo paginado 0</nick>", response);
        assertContentNotMatch("<nick>nuevo paginado 3</nick>", response);
    }
    
}