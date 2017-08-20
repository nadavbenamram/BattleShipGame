package BattleShipGameLogic;

import java.awt.*;

public class Player
{
	private int m_PlayerNumber;
	private PlayerStatistics m_Statistics;
	private GameBoard m_TraceBoard;
	private BattleShipGameBoard m_BattleShipsBoard;
	private Stopwatch m_StopWatch;

	public Player(int i_PlayerNumber)
	{
		m_PlayerNumber = i_PlayerNumber;
		m_Statistics = new PlayerStatistics(i_PlayerNumber);
		m_StopWatch = new Stopwatch();
		m_StopWatch.startThread();
	}

	public void TurnStarted()
	{
		m_StopWatch.ZeroTImer();
	}

	public void SetBattleShipBoard(BattleShipGameBoard i_Board)
	{
		m_BattleShipsBoard = i_Board;
	}

	public void SetTraceBoard(GameBoard i_Board)
	{
		m_TraceBoard = i_Board;
	}

	public AttackResult HitPoint(int i_AttackedPlayerIndex, Point i_Point)
	{
		AttackResult attackedResult;

		try
		{
			if(m_TraceBoard.GetCellSign(i_Point) != BoardSigns.EMPTY)
			{
				throw new IllegalArgumentException("Player " + m_PlayerNumber + " already attacked row = " + i_Point.y + " column = " + i_Point.x);
			}
			else //Attack
			{
				attackedResult = GameManager.Instance().Attack(m_PlayerNumber, i_AttackedPlayerIndex, i_Point);
				long stepDuration = m_StopWatch.GetTimeInSeconds();
				BoardSigns traceSign;
				switch(attackedResult.GetBeforeAttackSign())
				{
					case EMPTY:
						traceSign = BoardSigns.HIT;
						m_Statistics.Missed(stepDuration);
						break;
					case BATTLE_SHIP:
						traceSign = BoardSigns.BATTLE_SHIP_HIT;
						doWhenHitBattleShip(stepDuration);
						break;
					case MINE:
						doWhenAttackedMine(i_Point);
						traceSign = BoardSigns.HIT;
						break;
					default:
						throw new IllegalArgumentException("Player " + m_PlayerNumber + " attacked unkown board sign");
				}

				m_TraceBoard.SetCellSign(i_Point, traceSign);
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException("Player " + m_PlayerNumber + " attacked " + i_Point.toString() + " that out of the game board bounds");
		}

		return attackedResult;
	}

	private void doWhenHitBattleShip(long stepDuration)
	{
		m_Statistics.Hit(stepDuration);
	}

	private void doWhenAttackedMine(Point i_Point)
	{
		AttackedInPoint(i_Point); //TODO: should the player with the mine know which sign attacked?
	}

	public void SetMine(Point i_Point)
	{

	}

	//Input: point
	//Return: The attacked board sign (before the attack).
	public AttackResult AttackedInPoint(Point i_Point)
	{
		BoardSigns beforeSign = m_BattleShipsBoard.GetCellSign(i_Point);
		BoardSigns afterSign;
		AttackResult attackResult = new AttackResult((beforeSign));

		switch(beforeSign)
		{
			case EMPTY:
				afterSign = BoardSigns.HIT;
				break;
			case BATTLE_SHIP:
				afterSign = BoardSigns.BATTLE_SHIP_HIT;
				doWhenBattleShipHurt(i_Point, attackResult);
				break;
			case MINE:
				afterSign = BoardSigns.HIT;
				break;
			default:
				throw new IllegalArgumentException("Invalid board sign ("+beforeSign.GetValue()+") in player " + m_PlayerNumber + " in BattleShip board while attacked");
		}

		m_BattleShipsBoard.SetCellSign(i_Point, afterSign);

		return attackResult;
	}

	private void doWhenBattleShipHurt(Point i_point, AttackResult i_AttackResults)
	{
		boolean isBattleShipDrawn;
		BattleShip battleShip= m_BattleShipsBoard.GetBattleShipByPoint(i_point);
		battleShip.RemovePointFromObject(i_point);
		if(battleShip.IsObjectDrawn())
		{
			i_AttackResults.MarkBattleShipDrawn();
		}

		if(m_BattleShipsBoard.AliveBattleShips() == 0)
		{
			i_AttackResults.MarkPlayerWon();
		}
	}

	public PlayerStatistics GetPlayerStatistics()
	{
		return m_Statistics;
	}

	public GameBoard GetBattleShipGameBoard()
	{
		return m_BattleShipsBoard;
	}

	public GameBoard GetTraceGameBoard()
	{
		return m_TraceBoard;
	}

	public int GetPlayerNumber()
	{
		return m_PlayerNumber;
	}
}
