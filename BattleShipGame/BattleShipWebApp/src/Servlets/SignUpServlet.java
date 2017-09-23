package Servlets;

import Utils.Constants;
import Utils.ContextManager;
import Utils.SessionManager;
import Utils.User;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet
{
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ContextManager.SetContext(request.getServletContext());
		String username = (String) request.getParameter(Constants.USER_NAME_ATT_NAME);
		PrintWriter writer = response.getWriter();
		try
		{
			User user = new User(username);
			ContextManager.Instance().AddUser(user);
			HttpSession session = request.getSession(true);
			SessionManager.SetSession(session);
			SessionManager.Instance().SetCurrentUser(user);
			writer.println("YES");
		}
		catch (IllegalArgumentException e)
		{
			writer.println("NO");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleRequest(request, response);
	}
}
