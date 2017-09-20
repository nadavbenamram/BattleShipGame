package BattleShipGameLogic;

import java.awt.*;

public class Player
{
	private int m_PlayerNumber;
	private PlayerStatistics m_Statistics;
	private GameBoard m_TraceBoard;
	private BattleShipGameBoard m_BattleShipsBoard;
	private volatile Stopwatch m_StopWatch;
	private int m_TotalMines = 0;

	public Player()
	{}

	public int GetTotalMines()
	{
		return m_TotalMines;
	}

	public Player(int i_PlayerNumber)
	{
		m_PlayerNumber = i_PlayerNumber;
		m_Statistics = new PlayerStatistics(i_PlayerNumber);
		m_StopWatch = new Stopwatch(System.currentTimeMillis());
	}

	public Player Clone()
	{
		Player res = new Player();

		res.m_PlayerNumber = new Integer(m_PlayerNumber);
		res.m_Statistics = m_Statistics.Clone();
		res.m_TraceBoard = m_TraceBoard.Clone();
		res.m_BattleShipsBoard = (BattleShipGameBoard)(m_BattleShipsBoard.Clone());
		res.m_StopWatch = new Stopwatch(System.currentTimeMillis());
		res.m_TotalMines = m_TotalMines;

		return res;
	}

	public void TurnStarted()
	{
		if(m_Statistics.TotalSteps() > 0)
		{
			m_StopWatch.ZeroTimer();
		}
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
				throw new IllegalArgumentException("Player " + m_PlayerNumber + " already attacked row = " + i_Point.y + " column = " + (char)('A' - 1 + i_Point.x));
			}
			else //Attack
			{
				attackedResult = HandleAttack(i_AttackedPlayerIndex, i_Point, false);
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException("Player " + m_PlayerNumber + " attacked " + "Row: " + i_Point.y + " Column: " + (char)('A' - 1 + i_Point.x) + " that out of the game board bounds");
		}

		return attackedResult;
	}

	public AttackResult HandleAttack(int i_AttackedPlayerIndex, Point i_Point, boolean i_IsMineAttack)
	{
		AttackResult attackedResult = GameManager.Instance().Attack(m_PlayerNumber, i_AttackedPlayerIndex, i_Point, i_IsMineAttack);
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
				doWhenAttackedMine(i_Point, i_AttackedPlayerIndex, stepDuration);
				traceSign = BoardSigns.HIT;
				break;
			default:
				throw new IllegalArgumentException("Player " + m_PlayerNumber + " attacked unkown board sign");
		}

		m_TraceBoard.SetCellSign(i_Point, traceSign);

		return attackedResult;
	}

	private void doWhenHitBattleShip(long stepDuration)
	{
		m_Statistics.Hit(stepDuration);
	}

	private void doWhenAttackedMine(Point i_Point, int i_AttackedIdx, long stepDuration)
	{
		m_Statistics.Hit(stepDuration);
		GameManager.Instance().GetAllPlayers()[i_AttackedIdx].HandleAttack(m_PlayerNumber, i_Point, true);
	}

	public void SetMine(Point i_Point) throws Exception
	{
		if(m_TotalMines  < GameManager.Instance().GetMaxNumOfMines())
		{
			m_BattleShipsBoard.AddMine(i_Point);
			m_TotalMines++;
		}
		else
		{
			throw new IllegalArgumentException("Max num of mines for player is: " + m_TotalMines);
		}
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
				Mine mine = m_BattleShipsBoard.RemoveMine(i_Point);
				attackResult.MarkMineAttacked(mine);
				break;
			default:
				throw new IllegalArgumentException("Invalid board sign ("+beforeSign.GetValue()+") in player " + m_PlayerNumber + " in BattleShip board while attacked");
		}

		attackResult.AddAttackInfo(beforeSign.toString() + " cell (R: " + i_Point.y + " C: " + i_Point.x + ") attacked!");
		m_BattleShipsBoard.SetCellSign(i_Point, afterSign);

		return attackResult;
	}

	private void doWhenBattleShipHurt(Point i_point, AttackResult i_AttackResults)
	{
		boolean isBattleShipDrawn;

		BattleShip battleShip= m_BattleShipsBoard.GetBattleShipByPoint(i_point);
		i_AttackResults.MarkBattleShipAttacked(battleShip);
		battleShip.RemovePointFromObject(i_point);
		if(battleShip.IsObjectDrawn())
		{
			m_Statistics.AddPoints(battleShip.GetBattleShipScore());
			i_AttackResults.MarkBattleShipDrawn(battleShip);
			i_AttackResults.AddAttackInfo("BattleShip of player " + m_PlayerNumber + " was drawn!");
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

	public BattleShipGameBoard GetBattleShipGameBoard()
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
