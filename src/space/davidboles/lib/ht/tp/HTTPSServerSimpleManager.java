package space.davidboles.lib.ht.tp;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class HTTPSServerSimpleManager extends HTTPServerSimpleManager {

	
	
	public HTTPSServerSimpleManager(int port, File p12Cert, char[] password) throws Exception {
		super(port, new Object[]{p12Cert, password});
	}

	@Override
	void initialize(Object[] data) throws Exception {
		final File cert = (File) data[0];
		final char[] pass = (char[]) data[1];
		
        // setup the socket address
        InetSocketAddress address = new InetSocketAddress(this.port);

        // initialise the HTTPS server
        this.s = HttpsServer.create(address, 0);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        
        //Initialize cert FileInputStream
        FileInputStream fis = new FileInputStream(cert);
        
        //Initialize SSLContext
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(fis, pass);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, pass);
        sslContext.init(kmf.getKeyManagers(), null, null);
        
        //Create and use HttpsConfigurator
        ((HttpsServer)this.s).setHttpsConfigurator(new HttpsConfigurator(sslContext) {
            public void configure(HttpsParameters params) {
                // initialise the SSL context
                SSLContext c;
				try {
					c = SSLContext.getDefault();
					SSLEngine engine = c.createSSLEngine();
                    params.setNeedClientAuth(false);
                    params.setCipherSuites(engine.getEnabledCipherSuites());
                    params.setProtocols(engine.getEnabledProtocols());

                    // get the default parameters
                    SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
                    params.setSSLParameters(defaultSSLParameters);
				} catch (NoSuchAlgorithmException e) { }
            }
        });
        
        //Start server
        this.s.setExecutor(null); // creates a default executor
        this.s.start();
	}

}
