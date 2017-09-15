package BattleShipGameLogic;

import java.awt.*;

public class Mine extends  GameObject
{
	public Mine(BattleShipGameBoard i_GameBoard, Point i_Location)
	{
		super(i_GameBoard);
		m_Location = i_Location;
	}

	public Point GetLocation()
	{
		return m_Location;
	}
}
