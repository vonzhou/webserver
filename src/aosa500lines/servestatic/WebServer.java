package aosa500lines.servestatic;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

class RequestHandler extends AbstractHandler{

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// current work directory
		String base = System.getProperty("user.dir");
		// ϵͳĬ�ϵ��ļ��ָ���,ע��·���ָ����� path.separator
		String seperator = System.getProperty("file.separator");
		String requestPath = request.getPathInfo();
		
		/* Chrome ����������������: ���� /favicon.ico ���������⴦��
		 * ���� RFC 2616 ����Ӧ�÷��� 
		 * HTTP/1.0 404 <CRLF>
		 * <CRLF>
		 */
		if(requestPath.equals("/favicon.ico")){
			//TODO
			return ;
		}
			
		// ��þ���·�����ж��Ƿ����
		String absolueteRequestPath =(base +  request.getPathInfo()).replace("/", seperator);
		//�ж����·���Ƿ���һ���ļ�
		File path = new File(absolueteRequestPath);
		String res = "";
		String errorPage = "<html><body><h1>Error Accessing "+ requestPath +
							"</h1></body></html>";
		if(!path.isFile()){
			res = errorPage;
		}else{
			// ��ȡ�ļ�����
			res = TextFile.read(path);
		}
		
		
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentLength(res.length());
		baseRequest.setHandled(true);
		
		response.getWriter().println(res);
		
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
