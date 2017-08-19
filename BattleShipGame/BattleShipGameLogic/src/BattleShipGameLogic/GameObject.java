package BattleShipGameLogic;

import java.awt.*;

public abstract class GameObject
{
	protected BoardSigns m_BoardSign;
	protected int m_Length;
	protected Direction m_Direction;
	protected Point m_Location;
	protected Point[] m_ActivePoints;

	public GameObject()
	{
	}

	public BoardSigns GetBoardSign()
	{
		return m_BoardSign;
	}

	public int GetLength()
	{
		return m_Length;
	}

	public Direction GetDirection()
	{
		return m_Direction;
	}

	public Point GetLocation()
	{
		return m_Location;
	}
}
