package space.davidboles.lib.ht.tp;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.ssl.SSLContext;

public class HTTPSServerSimpleManager {
	
	HttpsServer s;
	
	public HTTPSServerSimpleManager(int port) throws IOException {
		this.s = HttpsServer.create(new InetSocketAddress(port), 100);
		this.s.setHttpsConfigurator(new HttpsConfigurator(SSLContext.getInstance(arg0)));
		this.s.setExecutor(null);
	    this.s.start();
	}
	
	public void addHandler(ContextualHttpHandler h) {
		this.s.createContext(h.getURLPath(), h);
	}
	
}
