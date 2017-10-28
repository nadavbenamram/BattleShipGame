package Servlets;

import BattleShipGameLogic.AttackResult;
import BattleShipGameLogic.BattleShip;
import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.Player;
import JsonObjects.GameJson;
import Utils.ContextManager;
import Utils.Game;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AttackServlet",  urlPatterns = {"/attack"})
public class AttackServlet extends HttpServlet
{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Point pointToAttack = new Point();
		pointToAttack.x = Integer.parseInt(request.getParameter("pointx"));
		pointToAttack.y = Integer.parseInt(request.getParameter("pointy"));
		String gameTitle = request.getParameter("gametitle");
		Game game = ContextManager.Instance().GetGameByTitle(gameTitle);
		String res = "";

		Player attack = game.GetGameManager().GetCurrentPlayer();
		try
		{
			GameManager.SetInstance(game.GetGameManager());
			AttackResult attackedResult = attack.HitPoint((attack.GetPlayerNumber() + 1) % 2, pointToAttack); //In this UI only two players playing...
			game.SetCurrentPlayerIdx(game.GetGameManager().GetNextPlayerIdx());
			if(attackedResult.GetPlayerWon())
			{
				game.DiAactivateGame();
			}
		}
		catch(Exception e)
		{
			res = e.getMessage();
		}

		//returning JSON objects, not HTML
		response.setContentType("application/json");
		try (PrintWriter out = response.getWriter()) {
			out.print(res);
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
