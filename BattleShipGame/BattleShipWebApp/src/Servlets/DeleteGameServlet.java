package Servlets;

import Utils.ContextManager;
import Utils.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static Utils.Constants.GAME_TITLE_PARAM_NAME;

@WebServlet(name = "DeleteGameServlet",  urlPatterns = {"/deletegame"})
public class DeleteGameServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String gameTitle = request.getParameter("gameName");
		String userName = request.getParameter("userName");
		Game game = ContextManager.Instance().GetGameByTitle(gameTitle);
		if(game.IsActive() == false && game.GetCurrentPlayersNum() == 0 && userName.equals(game.GetOwner().GetName()))
		{
			ContextManager.Instance().RemoveGame(game);
			PrintWriter out = response.getWriter();
			out.println("Game removed!");
			out.flush();
		}
		else
		{
			if(userName.equals(game.GetOwner().GetName()) == false)
			{
				response.getWriter().print("You can remove only your games");
			}
			else
			{
				response.getWriter().print("Can't remove non empty game!");
			}
		}
	}
}
