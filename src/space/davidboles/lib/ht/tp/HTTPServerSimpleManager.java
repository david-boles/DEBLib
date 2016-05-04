package space.davidboles.lib.ht.tp;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServerSimpleManager {
	
	final int port;
	HttpServer s;
	
	public HTTPServerSimpleManager(int port) throws Exception {
		this.port = port;
		initialize(null);
	}
	
	public HTTPServerSimpleManager(int port, Object[] data) throws Exception {
		this.port = port;
		initialize(data);
	}
	
	void initialize(Object[] data) throws Exception {
		this.s = HttpServer.create(new InetSocketAddress(this.port), 100);
		this.s.setExecutor(null);
	    this.s.start();
	}
	
	public void createContext(String context, HttpHandler handler) {
		this.s.createContext(context, handler);
	}
	
}
