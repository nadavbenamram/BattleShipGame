package Servlets;

import JsonObjects.GameJson;
import JsonObjects.UserJson;
import Utils.ContextManager;
import Utils.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "OnlineUsersServlet", urlPatterns = {"/userslist"})
public class OnlineUsersServlet extends HttpServlet
{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//returning JSON objects, not HTML
		response.setContentType("application/json");
		try (PrintWriter out = response.getWriter()) {
			Gson gson = new Gson();
			List<UserJson> onlineUsersList = ContextManager.Instance().GetAllConnectedUsersAsJson();
			String json = gson.toJson(onlineUsersList);
			out.println(json);
			out.flush();
		}
		catch (Exception error)
		{
			System.out.println(error.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
}
