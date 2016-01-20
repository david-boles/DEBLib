package space.davidboles.lib.ht.tp;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpHandler;

public abstract class DefinedHttpHandler implements HttpHandler {

	public String error404 = "HTTP Error 404. Please try different pages or content...";
	
	public DefinedHttpHandler() { }
	
	public DefinedHttpHandler(String error404) {
		this.error404 = error404;
	}
	
	public abstract String getURLPath();
	
	public void defaultError404(com.sun.net.httpserver.HttpExchange t) throws IOException {
		t.sendResponseHeaders(404, error404.length());
        OutputStream os = t.getResponseBody();
        os.write(error404.getBytes());
        os.close();
  }

}
