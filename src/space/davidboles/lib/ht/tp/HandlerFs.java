package space.davidboles.lib.ht.tp;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HandlerFs {
	
	public static void respond(HttpExchange t, int HTTPCode, String contentType, byte[] data) throws IOException {
		Headers h = t.getResponseHeaders();
		h.set("Content-Type", contentType);
		t.sendResponseHeaders(HTTPCode, 0);
		
        OutputStream os = t.getResponseBody();
        os.write(data);
        os.close();
	}
	
	//OK (200)
	public static void respondOK(HttpExchange t, String contentType, byte[] data) throws IOException {
        respond(t, 200, contentType, data);
	}
	
	public static void respondOKHTML(HttpExchange t, String message) throws IOException {
        respondOK(t, "text/html", message.getBytes());
	}
	
	public static void respondOKDefault(HttpExchange t) throws IOException {
		respondOKHTML(t, "OK");
	}
	
	//Not Found (404)
	public static void respondNotFound(HttpExchange t, String contentType, byte[] data) throws IOException {
        respond(t, 404, contentType, data);
	}
	
	public static void respondNotFoundHTML(HttpExchange t, String message) throws IOException {
        respondOK(t, "text/html", message.getBytes());
	}
	
	public static void respondNotFoundDefault(HttpExchange t) throws IOException {
		respondOKHTML(t, "Not Found");
	}

}
