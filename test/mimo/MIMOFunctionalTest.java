package mimo;
import java.util.regex.Pattern;

import play.mvc.Http;
import play.mvc.Http.Response;
import play.test.FunctionalTest;


public class MIMOFunctionalTest extends FunctionalTest {

	/**
	 * Nuevo método assert para comprobar que una expresión regular *no* aparece en el contenido
	 * @param pattern
	 * @param response
	 */
	public static void assertContentNotMatch(String pattern, Response response) {
        Pattern ptn = Pattern.compile(pattern);
        boolean ok = ptn.matcher(getContent(response)).find();
        assertFalse("Response content does match '" + pattern + "'", ok);
    }
    
	/**
	 * Método de utilidad para crear una request para un formato dado
	 * @param format
	 * @return
	 */
	public static Http.Request newRequest(String format) {
		String contentType = format.equals("json") ? "application/json" : "text/xml"; 
		Http.Request req = newRequest();
		req.headers.put("accept", new Http.Header("accept", contentType));
		req.format = format; // <- esto es importante en los tests
		return req;
	}


}
