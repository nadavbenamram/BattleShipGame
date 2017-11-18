package Servlets;

import Utils.ContextManager;
import Utils.SessionManager;
import Utils.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogOutServlet", urlPatterns = {"/logout"})
public class LogOutServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User user = SessionManager.Instance(request.getSession()).GetCurrentUser();
		try
		{
			ContextManager.Instance().RemoveUser(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		SessionManager.Instance(request.getSession()).SetCurrentUser(null);
		response.sendRedirect("signup/signup.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User user = SessionManager.Instance(request.getSession()).GetCurrentUser();
		try
		{
			ContextManager.Instance().RemoveUser(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		SessionManager.Instance(request.getSession()).SetCurrentUser(null);
		response.sendRedirect("signup/signup.jsp");
	}
}
