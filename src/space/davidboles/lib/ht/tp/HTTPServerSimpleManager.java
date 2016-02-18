package space.davidboles.lib.ht.tp;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class HTTPServerSimpleManager {
	
	HttpServer s;
	
	public HTTPServerSimpleManager(int port) throws IOException {
		this.s = HttpServer.create(new InetSocketAddress(port), 0);
		this.s.setExecutor(null);
	    this.s.start();
	}
	
	public void addHandler(ContextualHttpHandler h) {
		this.s.createContext(h.getURLPath(), h);
	}
	
}
