package Servlets;

import BattleShipGameLogic.AttackResult;
import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.Player;
import Utils.ContextManager;
import Utils.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PutMineServlet",  urlPatterns = {"/putmine"})
public class PutMineServlet extends HttpServlet
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
			GameManager.Instance().SetMine(attack, pointToAttack);
			game.SetCurrentPlayerIdx(game.GetGameManager().GetNextPlayerIdx());
		}
		catch(IllegalArgumentException e)
		{
			res = e.getMessage();
		}
		catch(Exception e)
		{
			res = "Invalid point to set mine - Should be:\n" +
			                                   "Row: 1 - " + GameManager.Instance().GetBoardSize() + "\n" +
			                                   "Column: A - " + (char)('A' + GameManager.Instance().GetBoardSize() - 1) + "\n";
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

