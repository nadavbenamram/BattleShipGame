package BattleShipGameLogic;

import java.awt.*;
import java.util.Arrays;

public abstract class GameObject
{
	protected BoardSigns m_BoardSign;
	protected int m_Length;
	protected Direction m_Direction;
	protected Point m_Location;
	protected Point[] m_ActivePoints;
	protected BattleShipGameBoard m_GameBoard;

	public GameObject(BattleShipGameBoard i_GameBoard)
	{
		m_GameBoard = i_GameBoard;
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

	public void SetActivePoints(Point[] i_ActivePoints)
	{
		m_ActivePoints = i_ActivePoints;
	}

	public boolean IsPointOfObject(Point i_Point)
	{
		return Arrays.asList(m_ActivePoints).contains(i_Point);
	}

	public void RemovePointFromObject(Point i_Point)
	{
		int newArrSize = m_ActivePoints.length - 1;
		if(newArrSize == 0)
		{
			m_GameBoard.ObjectDrawn(this);
			m_ActivePoints = null;
		}
		else
		{
			Point[] newArr = new Point[m_ActivePoints.length - 1];
			int i = 0;

			for(Point p : m_ActivePoints)
			{
				if(p.equals(i_Point) == false)
				{
					newArr[i++] = p;
				}
			}

			m_ActivePoints = newArr;
		}
	}

	public boolean IsObjectDrawn()
	{
		return m_ActivePoints == null || m_ActivePoints.length == 0;
	}
}
