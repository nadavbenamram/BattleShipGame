package Servlets;

import BattleShipGameLogic.GameManager;
import JsonObjects.FinalGameStatistics;
import JsonObjects.FinalPlayerStatistics;
import JsonObjects.UserJson;
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
import java.util.List;

import static Utils.Constants.GAME_LOAD_FAILED_ATT_NAME;
import static Utils.Constants.GAME_TITLE_PARAM_NAME;
import static Utils.Constants.USER_NAME_PARAM_NAME;

@WebServlet(name = "GameFinishedServlet",  urlPatterns = {"/gamefinished"})
public class GameFinishedServlet extends HttpServlet
{
	private int i = 0;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = SessionManager.Instance(request.getSession()).GetCurrentUser();
		String gameTitle = request.getParameter(GAME_TITLE_PARAM_NAME);
		String userName = request.getParameter(USER_NAME_PARAM_NAME);
		Game game = null;
		if(gameTitle != null)
		{
			game = ContextManager.Instance().GetGameByTitle(gameTitle);
			i++;
		}

		FinalGameStatistics gameStats = new FinalGameStatistics();
		GameManager gameManager = game.GetGameManager();
		gameStats.setGameDuration(gameManager.GetGameStatistics().GetGameDuration());
		gameStats.setTotalSteps(gameManager.GetGameStatistics().GetSteps());
		gameStats.getPlayersStatistics()[0] = new FinalPlayerStatistics();
		gameStats.getPlayersStatistics()[0].setAverageStepTime(gameManager.GetAllPlayers()[0].GetPlayerStatistics().GetAvgStepTimeInSeconds());
		gameStats.getPlayersStatistics()[0].setHit(gameManager.GetAllPlayers()[0].GetPlayerStatistics().GetHits());
		gameStats.getPlayersStatistics()[0].setMiss(gameManager.GetAllPlayers()[0].GetPlayerStatistics().GetMisses());
		gameStats.getPlayersStatistics()[0].setPoints(gameManager.GetAllPlayers()[0].GetPlayerStatistics().GetPoints());
		gameStats.getPlayersStatistics()[0].setPlayerName(game.GetUserByIdx(0).GetName());

		gameStats.getPlayersStatistics()[1] = new FinalPlayerStatistics();
		gameStats.getPlayersStatistics()[1].setAverageStepTime(gameManager.GetAllPlayers()[1].GetPlayerStatistics().GetAvgStepTimeInSeconds());
		gameStats.getPlayersStatistics()[1].setHit(gameManager.GetAllPlayers()[1].GetPlayerStatistics().GetHits());
		gameStats.getPlayersStatistics()[1].setMiss(gameManager.GetAllPlayers()[1].GetPlayerStatistics().GetMisses());
		gameStats.getPlayersStatistics()[1].setPoints(gameManager.GetAllPlayers()[1].GetPlayerStatistics().GetPoints());
		gameStats.getPlayersStatistics()[1].setPlayerName(game.GetUserByIdx(1).GetName());

		request.setAttribute("gameTitle", game.GetTitle());
		if(userName != null && userName.equals("") == false)
		{
			gameStats.setPlayerLeft(true);
			gameStats.setPlayerLeftName(userName);
		}

		if (game != null && i%2 == 0)
		{
			game.ResetGame();
		}

		request.setAttribute("gamestats", gameStats);
		request.getRequestDispatcher("/games/gamefinished.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			processRequest(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			processRequest(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
