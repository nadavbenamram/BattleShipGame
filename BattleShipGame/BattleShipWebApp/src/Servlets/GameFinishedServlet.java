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

@WebServlet(name = "GameFinishedServlet",  urlPatterns = {"/gamefinished"})
public class GameFinishedServlet extends HttpServlet
{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User user = SessionManager.Instance(request.getSession()).GetCurrentUser();
		String gameTitle = request.getParameter(GAME_TITLE_PARAM_NAME);
		String userName = request.getParameter(USER_NAME_PARAM_NAME);
		Game game = null;
		if(gameTitle != null)
		{
			game = ContextManager.Instance().GetGameByTitle(gameTitle);
			game.DiAactivateGame();
		}

		request.setAttribute("gameTitle", game.GetTitle());
		if(userName != null)
		{
			request.setAttribute("leftusername", userName);
		}

		request.getRequestDispatcher("/games/gamefinished.jsp").forward(request,response);
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
