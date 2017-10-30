package Servlets;

import Utils.ContextManager;
import Utils.Game;
import Utils.SessionManager;
import Utils.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static Utils.Constants.GAME_LOAD_FAILED_ATT_NAME;
import static Utils.Constants.GAME_TITLE_PARAM_NAME;
import static Utils.Constants.USER_NAME_PARAM_NAME;

@WebServlet(name = "JoinWatchGameServlet", urlPatterns = {"/joinwatchgame"})
public class JoinWatchGameServlet extends HttpServlet
{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String gameTitle = request.getParameter(GAME_TITLE_PARAM_NAME);
		try
		{
			request.setAttribute(GAME_TITLE_PARAM_NAME, gameTitle);
		}
		catch (Exception e)
		{
			request.setAttribute(GAME_LOAD_FAILED_ATT_NAME, e.getMessage());
		}
		finally
		{
			request.getRequestDispatcher("/games/watchgame.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
}

