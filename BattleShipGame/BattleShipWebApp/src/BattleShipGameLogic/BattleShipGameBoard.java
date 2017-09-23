package BattleShipGameLogic;

import generated.BattleShipGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BattleShipGameBoard extends GameBoard
{
	private ArrayList<BattleShip> m_BattleShips;
	private ArrayList<Mine>  m_Mines;
	private Integer m_NumOfMines;

	public BattleShipGameBoard()
	{
	}

	public BattleShipGameBoard(int i_BoardSize,
	                           BattleShipGame.Boards.Board i_BoardConfiguration,
	                           BattleShipGame.ShipTypes i_ShipTypes,
	                           BattleShipGame.Mine i_Mines)
	{
		super(i_BoardSize);
		fillBoard(i_BoardConfiguration, i_ShipTypes, i_Mines);
	}
	int t = 0;
	@Override
	public GameBoard Clone()
	{
		BattleShipGameBoard res = new BattleShipGameBoard();


		res.m_BoardSize = m_BoardSize;
		res.m_BattleShips = new ArrayList<BattleShip>(m_BattleShips.size());
		for (BattleShip battleShip : m_BattleShips)
		{
			BattleShip newBattleShip = new BattleShip(null, null);
			newBattleShip.SetLength(battleShip.GetLength());
			res.m_BattleShips.add(newBattleShip);
		}
		res.m_Mines = new ArrayList<Mine>(m_Mines.size());
		for(Mine mine : m_Mines){
			res.m_Mines.add(new Mine(null, null));
		}

		res.m_Board = CloneBoard();

		return res;
	}

	private void fillBoard(BattleShipGame.Boards.Board i_boardConfiguration, BattleShipGame.ShipTypes i_shipTypes, BattleShipGame.Mine i_mines)
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
			battleShip = new BattleShip(shipId, this);
			battleShip.FillShipDetails(i_shipTypes, ship);
			addBattleShipToBoard(battleShip);
		}

		m_NumOfMines = i_mines != null ? i_mines.getAmount() : 0;
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
		if(i_BattleShip.GetCategory() == BattleShipCategory.L_SHAPE)
		{
			addLBattleShipToBoard(i_BattleShip);
		}
		else
		{
			addObjectToBoard(i_BattleShip);
		}

		m_BattleShips.add(i_BattleShip);
	}

	private void addObjectToBoard(GameObject i_GameObject)
	{
		Point objLocation = i_GameObject.GetLocation();
		BoardSigns objSign = i_GameObject.GetBoardSign();
		int length = i_GameObject.GetLength();
		Point p = (Point)objLocation.clone();
		Point[] pointsToDraw = new Point[length];

		for(int i = 0; i< length; ++i)
		{
			checkPointValidation(p, i_GameObject, pointsToDraw);
			pointsToDraw[i] = (Point)p.clone();
			moveToNextPoint(p, i_GameObject, true);
		}

		i_GameObject.SetActivePoints(pointsToDraw);
		drawPoints(pointsToDraw, objSign);
	}

	private void addLBattleShipToBoard(BattleShip i_BattleShip)
	{
		Point origin = i_BattleShip.GetLocation();
		BoardSigns objSign = i_BattleShip.GetBoardSign();
		int lengthEachSide = i_BattleShip.GetLength();
		Point p = (Point)origin.clone();
		Point[] pointsToDraw = new Point[(lengthEachSide * 2) - 1];
		int i;

		for(i = 0; i< lengthEachSide; ++i)
		{
			checkPointValidation(p, i_BattleShip, pointsToDraw);
			pointsToDraw[i] = (Point)p.clone();
			moveToNextPoint(p, i_BattleShip, true);
		}

		p = (Point)origin.clone();
		for(; i< lengthEachSide * 2; ++i)
		{
			if(i != lengthEachSide)
			{
				checkPointValidation(p, i_BattleShip, pointsToDraw);
				pointsToDraw[i - 1] = (Point)p.clone();
			}

			moveToNextPoint(p, i_BattleShip, false);
		}

		i_BattleShip.SetActivePoints(pointsToDraw);
		drawPoints(pointsToDraw, objSign);
	}

	private void drawPoints(Point[] i_PointsToDraw, BoardSigns i_BoardSign)
	{
		for(Point p : i_PointsToDraw)
		{
			m_Board[p.y][p.x] = i_BoardSign;
		}
	}

	private void moveToNextPoint(Point p, GameObject i_GameObject, boolean i_IsFirstSide)
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
				if(i_IsFirstSide)
				{
					p.x--;
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
				if(i_IsFirstSide)
				{
					p.x--;
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
				if(i_IsFirstSide)
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
				if(i_IsFirstSide)
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

	private void checkPointValidation(Point p, GameObject i_GameObject, Point[] i_ObjectPoints)
	{
		if(false == checkIfInsideBoardBounds(p))
		{
			throw new IndexOutOfBoundsException("Object " + i_GameObject.toString() + " out of game board bounds");
		}

		for(int x = p.x-1; x < p.x + 2; x++)
		{
			for(int y = p.y-1; y <  p.y + 2; y++)
			{
				if(true == checkIfInsideBoardBounds(new Point(x,y)))
				{
					if(m_Board[y][x] == BoardSigns.BATTLE_SHIP || m_Board[y][x] == BoardSigns.MINE)
					{
						//Patch: For L support
						if(i_ObjectPoints != null &&
						   m_Board[y][x] == BoardSigns.BATTLE_SHIP &&
						   GameManager.Instance().IsAdvancedGameType() &&
						   Arrays.asList(i_ObjectPoints).contains(new Point(x, y)))
						{
							continue;
						}

						throw new UnsupportedOperationException("Object " + i_GameObject.toString() + " near or on another object");
					}
				}
			}
		}
	}

	private boolean checkIfInsideBoardBounds(Point i_Point)
	{
		return (i_Point.x <= m_BoardSize && i_Point.x >= 1 && i_Point.y <= m_BoardSize && i_Point.y >= 1);
	}

	public BattleShip GetBattleShipByPoint(Point i_Point)
	{
		for(BattleShip battleShip : m_BattleShips)
		{
			if(battleShip.IsPointOfObject(i_Point))
			{
				return battleShip;
			}
		}

		throw new IllegalArgumentException("The point " + i_Point.toString() + "isn't of any BattleShip");
	}

	public void ObjectDrawn(GameObject i_Obj)
	{
		if(i_Obj instanceof  BattleShip)
		{
			m_BattleShips.remove(i_Obj);
		}
		else if(i_Obj instanceof  Mine)
		{
			m_Mines.remove(i_Obj);
		}
		else
		{
			throw new IllegalArgumentException("Unknown object type to be drawn");
		}
	}

	public int AliveBattleShips()
	{
		return m_BattleShips.size();
	}

	public void AddMine(Point i_Point) throws Exception
	{
		Mine mine;

		if(GetCellSign(i_Point) != BoardSigns.EMPTY)
		{
			throw new IllegalArgumentException("can't put mine on non-empty cell");
		}
		else if(GameManager.Instance().GetMaxNumOfMines() == null)
		{
			throw new IllegalArgumentException("Mines not allowed with this game settings");
		}
		else if(m_Mines.size() == GameManager.Instance().GetMaxNumOfMines())
		{
			throw new IllegalArgumentException("The max num of mines is: " + GameManager.Instance().GetMaxNumOfMines());
		}
		else
		{
			try
			{
				checkPointValidation(i_Point, mine = new Mine(this, i_Point), null);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException("mine can't be near Battleship");
			}
			m_Mines.add(mine);
			SetCellSign(i_Point, BoardSigns.MINE);
		}
	}

	public Mine RemoveMine(Point i_Point)
	{
		Mine mine = null;

		for(Mine m1 : m_Mines)
		{
			if (m1.GetLocation().equals(i_Point))
			{
				mine = m1;
				break;
			}
		}

		if(mine == null)
		{
			throw new IllegalArgumentException("Couldn't find mine in " + i_Point.toString());
		}

		m_Mines.remove(mine);

		return mine;
	}

	public ArrayList<BattleShip> GetBattleShip()
	{
		return m_BattleShips;
	}
}
