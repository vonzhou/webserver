package aosa500lines.echorequestinfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		
		SimpleDateFormat dateform = new SimpleDateFormat();
		String date = dateform.format(new Date());
		String end = request.getRemoteAddr()+":"+request.getRemotePort();
		String cmd = request.getMethod();
		String path = request.getPathInfo();
		
		reponse.setContentType("text/html;charset=utf-8");
		reponse.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		
		reponse.getWriter().println("<h1><font color=red>"+date+"<font></h1>");
		reponse.getWriter().println("<h1><font color=red>"+end+"<font></h1>");
		reponse.getWriter().println("<h1><font color=red>"+cmd+"<font></h1>");
		reponse.getWriter().println("<h1><font color=red>"+path+"<font></h1>");
		
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
