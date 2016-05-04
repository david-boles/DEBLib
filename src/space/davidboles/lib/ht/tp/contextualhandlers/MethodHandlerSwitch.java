package space.davidboles.lib.ht.tp.contextualhandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import space.davidboles.lib.ht.tp.ContextualHttpHandler;

public class MethodHandlerSwitch extends ContextualHttpHandler{
	public static final int METHOD_DEFAULT = 0;
	public static final int METHOD_GET = 1;
	public static final int METHOD_HEAD = 2;
	public static final int METHOD_POST = 3;
	public static final int METHOD_PUT = 4;
	public static final int METHOD_DELETE = 5;
	public static final int METHOD_CONNECT = 6;
	public static final int METHOD_OPTIONS = 7;
	public static final int METHOD_TRACE = 8;
	
	protected HttpHandler[] methodSpecificHandlers = new HttpHandler[9];
	
	protected final String context;
	
	public MethodHandlerSwitch(String context, HttpHandler defaultHandler) {
		this.context = context;
		if(defaultHandler == null) throw new IllegalArgumentException("Default handler is null.");
		this.methodSpecificHandlers[0] = defaultHandler;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		String method = arg0.getRequestMethod();
		
		HttpHandler handler = this.methodSpecificHandlers[METHOD_DEFAULT];
		if(method.equals("GET")) handler = this.getMethodHandler(METHOD_GET);
		if(method.equals("HEAD")) handler = this.getMethodHandler(METHOD_HEAD);
		if(method.equals("POST")) handler = this.getMethodHandler(METHOD_POST);
		if(method.equals("PUT")) handler = this.getMethodHandler(METHOD_PUT);
		if(method.equals("DELETE")) handler = this.getMethodHandler(METHOD_DELETE);
		if(method.equals("CONNECT")) handler = this.getMethodHandler(METHOD_CONNECT);
		if(method.equals("OPTIONS")) handler = this.getMethodHandler(METHOD_OPTIONS);
		if(method.equals("TRACE")) handler = this.getMethodHandler(METHOD_TRACE);
		
		handler.handle(arg0);
	}
	
	public HttpHandler setMethodHandler(int method, HttpHandler handler) {
		HttpHandler old = this.methodSpecificHandlers[method];
		this.methodSpecificHandlers[method] = handler;
		return old;
	}
	
	public HttpHandler getMethodHandler(int method) {
		HttpHandler handler = this.methodSpecificHandlers[method];
		if(handler == null) handler = this.methodSpecificHandlers[METHOD_DEFAULT];
		return handler;
	}

	@Override
	public String getContext() {
		return this.context;
	}
	
}
