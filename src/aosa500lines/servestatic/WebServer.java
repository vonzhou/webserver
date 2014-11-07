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
		// 系统默认的文件分隔符,注意路径分隔符是 path.separator
		String seperator = System.getProperty("file.separator");
		String requestPath = request.getPathInfo();
		
		/* Chrome 浏览器存在这个问题: 请求 /favicon.ico ，所以特殊处理
		 * 按照 RFC 2616 至少应该返回 
		 * HTTP/1.0 404 <CRLF>
		 * <CRLF>
		 */
		if(requestPath.equals("/favicon.ico")){
			//TODO
			return ;
		}
			
		// 获得绝对路径，判断是否存在
		String absolueteRequestPath =(base +  request.getPathInfo()).replace("/", seperator);
		//判断这个路径是否是一个文件
		File path = new File(absolueteRequestPath);
		String res = "";
		String errorPage = "<html><body><h1>Error Accessing "+ requestPath +
							"</h1></body></html>";
		if(!path.isFile()){
			res = errorPage;
		}else{
			// 读取文件内容
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
