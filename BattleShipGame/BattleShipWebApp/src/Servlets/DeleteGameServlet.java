package Servlets;

import Utils.ContextManager;
import Utils.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static Utils.Constants.GAME_TITLE_PARAM_NAME;

@WebServlet(name = "DeleteGameServlet",  urlPatterns = {"/deletegame"})
public class DeleteGameServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String gameTitle = request.getParameter("gameName");
		Game game = ContextManager.Instance().GetGameByTitle(gameTitle);
		//if(game.IsActive() == false && game.GetCurrentPlayersNum() == 0)
		//{
		ContextManager.Instance().RemoveGame(game);
		//}
		//else
		//{
		//response.getWriter().print("Can't remove non empty game!");
		//}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String gameTitle = request.getParameter("gameName");
		Game game = ContextManager.Instance().GetGameByTitle(gameTitle);
		//if(game.IsActive() == false && game.GetCurrentPlayersNum() == 0)
		//{
			ContextManager.Instance().RemoveGame(game);
		//}
		//else
		//{
			//response.getWriter().print("Can't remove non empty game!");
		//}
	}
}
