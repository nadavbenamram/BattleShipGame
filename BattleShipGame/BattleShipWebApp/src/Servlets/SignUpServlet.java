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
		String username = (String) request.getParameter(Constants.USER_NAME_PARAM_NAME);
		PrintWriter writer = response.getWriter();
		try
		{
			if(username == null || username.equals(""))
			{
				writer.println("user name can't be empty!");
			}
			else
			{
				if(SessionManager.Instance(request.getSession(true)).GetCurrentUser() != null)
				{
					writer.println("You are already connected with the username " + SessionManager.Instance(request.getSession()).GetCurrentUser().GetName());
				}
				else
				{
					User user = new User(username);
					ContextManager.Instance().AddUser(user);
					SessionManager.Instance(request.getSession(true)).SetCurrentUser(user);
					writer.println("YES");
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			writer.println(e.getMessage());
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
