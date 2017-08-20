package BattleShipGameLogic;

import com.sun.javaws.exceptions.InvalidArgumentException;
import generated.BattleShipGame;

import java.awt.*;

public class BattleShip extends GameObject
{
	private String m_Id;
	BattleShipCategory m_Category;
	private int m_Score;

	public BattleShip(String i_Id, BattleShipGameBoard i_GameBoard)
	{
		super(i_GameBoard);
		m_Id = i_Id;
	}

	public void FillShipDetails(BattleShipGame.ShipTypes i_ShipTypes, BattleShipGame.Boards.Board.Ship i_Ship)
	{
		for (BattleShipGame.ShipTypes.ShipType shipType : i_ShipTypes.getShipType())
		{
			if(shipType.getId().equals(m_Id))
			{
				m_Category = BattleShipCategory.valueOf(shipType.getCategory());
				m_Length = shipType.getLength();
				m_Score = shipType.getScore();
				m_BoardSign = BoardSigns.BATTLE_SHIP;
				m_Location = new Point(i_Ship.getPosition().getY(), i_Ship.getPosition().getX());
				m_Direction = Direction.valueOf(i_Ship.getDirection());
				validateShipParametes();

				return;
			}
		}

		throw new IllegalArgumentException(m_Id + " is invalid shipTypeId");
	}

	private void validateShipParametes()
	{
		if(m_Length <= 0)
		{
			throw new IllegalArgumentException(m_Id + " Length is negative");
		}

		if(m_Score <= 0)
		{
			throw new IllegalArgumentException(m_Id + " Score is negative");
		}
	}
}
