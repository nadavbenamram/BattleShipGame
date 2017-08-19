package BattleShipGameLogic;

import java.awt.*;

public class Player
{
	private int m_PlayerNumber;
	private PlayerStatistics m_Statistics;
	private GameBoard m_TraceBoard;
	private BattleShipGameBoard m_BattleShipsBoard;

	public Player(int i_PlayerNumber)
	{
		m_PlayerNumber = i_PlayerNumber;
		m_Statistics = new PlayerStatistics();
	}

	public void SetBattleShipBoard(BattleShipGameBoard i_Board)
	{
		m_BattleShipsBoard = i_Board;
	}

	public void SetTraceBoard(GameBoard i_Board)
	{
		m_TraceBoard = i_Board;
	}

	public void HitPoint(Point i_Point)
	{
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
}
