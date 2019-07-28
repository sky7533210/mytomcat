package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Enumeration<String> names=request.getParameterNames();
		while (names.hasMoreElements()) {
			String name=names.nextElement();
			System.out.println(name+"==>"+ Arrays.toString(request.getParameterValues(name)));
		}		
		PrintWriter out= response.getWriter();
		out.write("徐世昌是世界上最聪明的人");
		response.getOutputStream().write("徐世昌果真是世界上最聪明的人".getBytes());
	}

}
