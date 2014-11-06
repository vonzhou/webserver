package aosa500lines.servestatic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

class RequestHandler extends AbstractHandler{

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, ServletException {
		// current work directory
		String base = System.getProperty("user.dir");
		String requestPath = request.getPathInfo();
		int index = requestPath.indexOf('/');
		
		requestPath = requestPath.substring(index);
		System.out.println(requestPath);
		/*
		File currentDirectory = new File(".");
		System.out.println(currentDirectory.getCanonicalPath());
		*/ // 和上面得到的结果一样 E:\GitHub\MyProject\webserver
		
		reponse.setContentType("text/html;charset=utf-8");
		reponse.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		
		reponse.getWriter().println("<h1><font color=red>"+requestPath+"<font></h1>");
		reponse.getWriter().println("<h1><font color=red>"+base+"<font></h1>");
		
	}
	
}

public class WebServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new RequestHandler());
		
		server.start();
		server.join();
		
	}
}
