package BattleShipGameLogic;

import generated.BattleShipGame;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BattleShipGameBoard extends GameBoard
{
	private ArrayList<BattleShip> m_BattleShips;
	private ArrayList<Mine>  m_Mines;
	private Integer m_NumOfMines;

	public BattleShipGameBoard(int i_BoardSize,
	                           generated.BattleShipGame.Boards.Board i_BoardConfiguration,
	                           generated.BattleShipGame.ShipTypes i_ShipTypes,
	                           generated.BattleShipGame.Mine i_Mines)
	{
		super(i_BoardSize);
		fillBoard(i_BoardConfiguration, i_ShipTypes, i_Mines);
	}

	private void fillBoard(BattleShipGame.Boards.Board i_boardConfiguration, BattleShipGame.ShipTypes i_shipTypes, generated.BattleShipGame.Mine i_mines)
	{
		int totalShips = 0;
		int totalMines = 0;
		String shipId;
		BattleShip battleShip;

		m_BattleShips = new ArrayList<>();
		m_Mines = new ArrayList<>();

		for(BattleShipGame.Boards.Board.Ship ship : i_boardConfiguration.getShip())
		{
			shipId = ship.getShipTypeId();
			battleShip = new BattleShip((shipId));
			battleShip.FillShipDetails(i_shipTypes, ship);
			addBattleShipToBoard(battleShip);
		}

		m_NumOfMines = i_mines.getAmount();
		validateNumOfMines();
	}

	private void validateNumOfMines()
	{
		if(m_NumOfMines != null && m_NumOfMines < 0)
		{
			throw new IllegalArgumentException("Num of mines should be non-negative");
		}
	}

	private void addBattleShipToBoard(BattleShip i_BattleShip)
	{
		addObjectToBoard(i_BattleShip);
		m_BattleShips.add(i_BattleShip);
	}

	private void addObjectToBoard(GameObject i_GameObject)
	{
		Point objLocation = i_GameObject.GetLocation();
		BoardSigns objSign = i_GameObject.GetBoardSign();
		int length = i_GameObject.GetLength();
		Point p = objLocation;
		Direction direction = i_GameObject.GetDirection();
		Point[] pointsToDraw = new Point[length];

		for(int i = 0; i< length; ++i)
		{
			checkPointValidation(p, i_GameObject);
			pointsToDraw[i] = (Point)p.clone();
			moveToNextPoint(p, i, i_GameObject);
		}

		drawPoints(pointsToDraw, objSign);
	}

	private void drawPoints(Point[] i_PointsToDraw, BoardSigns i_BoardSign)
	{
		for(Point p : i_PointsToDraw)
		{
			m_Board[p.x][p.y] = i_BoardSign;
		}
	}

	private void moveToNextPoint(Point p, int i, GameObject i_GameObject)
	{
		int length = i_GameObject.GetLength();
		Direction direction = i_GameObject.GetDirection();

		switch(direction)
		{
			case ROW:
			{
				p.x++;
				break;
			}

			case COLUMN:
			{
				p.y++;
				break;
			}

			case RIGHT_DOWN:
			{
				if(i < (length/2 + 1))
				{
					p.x++;
					break;
				}
				else
				{
					p.y++;
					break;
				}
			}

			case RIGHT_UP:
			{
				if(i < (length/2 + 1))
				{
					p.x++;
					break;
				}
				else
				{
					p.y--;
					break;
				}
			}

			case UP_RIGHT:
			{
				if(i < (length/2 + 1))
				{
					p.y--;
					break;
				}
				else
				{
					p.x++;
					break;
				}
			}

			case DOWN_RIGHT:
			{
				if(i < (length/2 + 1))
				{
					p.y++;
					break;
				}
				else
				{
					p.x++;
					break;
				}
			}
		}
	}

	private void checkPointValidation(Point p, GameObject i_GameObject)
	{
		if(checkIfInsideBoardBounds(p))
		{
			throw new IndexOutOfBoundsException("Object " + i_GameObject.toString() + " out of game board bounds");
		}

		for(int x = p.x-1; x < p.x + 3; x++)
		{
			for(int y = p.y-1; y <  p.y + 3; y++)
			{
				if(checkIfInsideBoardBounds(new Point(x,y)))
				{
					if(m_Board[x][y] != BoardSigns.EMPTY)
					{
						throw new UnsupportedOperationException("Object " + i_GameObject.toString() + " near or on another object");
					}
				}
			}
		}
	}

	private boolean checkIfInsideBoardBounds(Point i_Point)
	{
		return (i_Point.x >= m_BoardSize || i_Point.x < 0 || i_Point.y >= m_BoardSize || i_Point.y < 0);
	}

}
