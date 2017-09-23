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

@WebServlet(name = "JoinGameServlet", urlPatterns = {"/joingame"})
public class JoinGameServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User user = SessionManager.Instance(request.getSession()).GetCurrentUser();
		String gameTitle = request.getParameter(GAME_TITLE_PARAM_NAME);
		Game game = null;
		if(gameTitle != null)
		{
			game = ContextManager.Instance().GetGameByTitle(gameTitle);
		}

		try
		{
			boolean startGame = game.AddUser(user);
		}
		catch (Exception e)
		{
			request.setAttribute(GAME_LOAD_FAILED_ATT_NAME, e.getMessage());
		}
		finally
		{
			request.getRequestDispatcher("/games/games.jsp").forward(request,response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}
}
