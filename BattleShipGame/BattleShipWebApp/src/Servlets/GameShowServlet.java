package Servlets;

import BattleShipGameLogic.Player;
import BattleShipGameLogic.PlayerStatistics;
import JsonObjects.GameJson;
import JsonObjects.GamePlayerJson;
import JsonObjects.PlayerStatsJson;
import Utils.Constants;
import Utils.ContextManager;
import Utils.Game;
import Utils.SessionManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GameShowServlet", urlPatterns = {"/gameshow"})
public class GameShowServlet extends HttpServlet
{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//returning JSON objects, not HTML
		response.setContentType("application/json");
		try (PrintWriter out = response.getWriter()) {
			String username = (String) request.getParameter("userName");
			String gamename = (String) request.getParameter("gameName");
			String json = username;
			if(json != null)
			{
				Gson gson = new Gson();
				GamePlayerJson gamePlayerJson = new GamePlayerJson();
				PlayerStatsJson playerStatistics = new PlayerStatsJson();
				playerStatistics.setName(SessionManager.Instance(request.getSession()).GetCurrentUser().GetName());
				Game game = ContextManager.Instance().GetGameByTitle(gamename);
				playerStatistics.setScore(game.GetGameManager().GetAllPlayers()[(SessionManager.Instance(request.getSession()).GetCurrentUser().getPlayerIndex(game.GetTitle()) + 1) % 2].GetPlayerStatistics().GetPoints());
				gamePlayerJson.setPlayerStatistics(playerStatistics);
				Player player = game.GetGameManager().GetAllPlayers()[SessionManager.Instance(request.getSession()).GetCurrentUser().getPlayerIndex(game.GetTitle())];
				gamePlayerJson.Set(game, player);
				if(game.GetActivePlayersNum() == 1)
				{
					gamePlayerJson.setIsWaitingToSecondPlayer(true);
				}
				else
				{
					gamePlayerJson.setIsWaitingToSecondPlayer(false);
				}

				if(game.GetActivePlayersNum() == 2 && game.GetGameManager().GetGameStatistics().GetSteps() == 0)
				{
					gamePlayerJson.setGameStartedNow(true);
				}
				else
				{
					gamePlayerJson.setGameStartedNow(false);
				}

				if(game.IsActive() == false && game.GetIsStarted() == true)
				{
					gamePlayerJson.setGameFinished(true);
				}

				json = gson.toJson(gamePlayerJson);
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
