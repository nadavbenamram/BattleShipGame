package Servlets;

import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.Player;
import JsonObjects.GamePlayerJson;
import JsonObjects.PlayerStatsJson;
import Utils.ContextManager;
import Utils.Game;
import Utils.SessionManager;
import Utils.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "WatchGameServlet", urlPatterns = {"/watchgame"})
public class WatchGameServlet extends HttpServlet
{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//returning JSON objects, not HTML
		response.setContentType("application/json");
		try (PrintWriter out = response.getWriter()) {
			String gamename = (String) request.getParameter("gameName");
			String json;

			if(gamename != null && gamename.equals("") != true)
			{
				Game game = ContextManager.Instance().GetGameByTitle(gamename);
				GameManager gameManager = game.GetGameManager();
				Player player = gameManager.GetCurrentPlayer();
				Gson gson = new Gson();
				GamePlayerJson gamePlayerJson = new GamePlayerJson();
				PlayerStatsJson playerStatistics = new PlayerStatsJson();
				playerStatistics.setName(game.GetCurrentUser().GetName());
				playerStatistics.setScore(game.GetGameManager().GetAllPlayers()[(game.GetCurrentUser().getPlayerIndex(game.GetTitle()) + 1) % 2].GetPlayerStatistics().GetPoints());
				gamePlayerJson.setPlayerStatistics(playerStatistics);
				gamePlayerJson.Set(game, player);

				if(true == game.GetIsStarted() && game.IsActive() == false)
				{
					gamePlayerJson.setGameFinished(true);
				}

				json = gson.toJson(gamePlayerJson);
			}
			else
			{
				json = "OK";
			}

			out.println(json);
			out.flush();
		}
		catch (Exception error)
		{
			System.out.println(error.getMessage());
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

