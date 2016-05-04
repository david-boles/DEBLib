package space.davidboles.lib.ht.tp.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import space.davidboles.lib.ht.tp.HandlerFs;
import space.davidboles.lib.ht.tp.MIMESwitcher;

public class FolderHttpHandler implements HttpHandler {

	public String urlPath;
	public File directory;
	
	public FolderHttpHandler(String urlPath, File directory) {
		this.urlPath = urlPath;
		this.directory = directory;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		String root = directory.getCanonicalPath();
		URI uri = t.getRequestURI();
		try {
			uri = new URI(uri.toString().substring(urlPath.length()));
		} catch (URISyntaxException e) {
			System.out.println("Failed: " + uri.toString().substring(urlPath.length()));
		}
		
		String path = uri.getPath();
		if(!path.startsWith("/")) path = "/" + path;
		
		File file = new File(root + path).getCanonicalFile();
		
		if (!file.isFile()) {
			HandlerFs.respondNotFoundDefault(t);
		} else {
			// Object exists and is a file: accept with response code 200.
			String mime = MIMESwitcher.toMIME(file.getCanonicalPath());
			if (mime == null) mime = "text/html";
		
			Headers h = t.getResponseHeaders();
			h.set("Content-Type", mime);
			t.sendResponseHeaders(200, 0);
		
			OutputStream os = t.getResponseBody();
			FileInputStream fs = new FileInputStream(file);
			final byte[] buffer = new byte[0x10000];
			int count = 0;
			while ((count = fs.read(buffer)) >= 0) {
			  os.write(buffer,0,count);
			}
			fs.close();
			os.close();
		 }
		
	}

	

}
