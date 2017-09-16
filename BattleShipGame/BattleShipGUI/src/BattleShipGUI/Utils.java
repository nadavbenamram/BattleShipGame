package BattleShipGUI;

import BattleShipGameLogic.AttackResult;
import BattleShipGameLogic.BoardSigns;
import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.Player;

import java.awt.*;

public final class Utils
{
	public static boolean isGameFinished = false;
	public static boolean isGameStarted = false;

	public static AttackResult MakeMove(int i_Row, int i_Column) throws Exception
	{
		Point pointToAttack = null;

		if(isGameStarted == false)
		{
			throw new Exception("You can make move after you start a game");
		}

		pointToAttack = new Point(i_Column, i_Row);
		Player attack = GameManager.Instance().GetCurrentPlayer();
		AttackResult attackedResult = attack.HitPoint((attack.GetPlayerNumber() + 1) % 2, pointToAttack); //In this UI only two players playing...
		if(attackedResult.GetBeforeAttackSign() == BoardSigns.BATTLE_SHIP)
		{
			System.out.println("Player " + attack.GetPlayerNumber() + " hit BattleShip!");
		}

		if(attackedResult.GetIsBattleShipDrawn())
		{
			System.out.println("Player " + attack.GetPlayerNumber() + " drawn BattleShip!!!");
		}

		if(attackedResult.GetPlayerWon())
		{
			isGameFinished = true;
		}
		System.out.println("clicked");

		return attackedResult;
	}
}
