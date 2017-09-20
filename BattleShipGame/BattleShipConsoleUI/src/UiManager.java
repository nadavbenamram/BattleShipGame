import BattleShipGameLogic.*;
import com.sun.deploy.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class UiManager
{
	private static boolean m_XmlLoaded = false;
	private static boolean m_GameStarted = false;
	private static int m_BoardSize;

	public static void main(String[] args) throws IOException
	{
		Menu menu = new Menu();
		int userChoice;

		while (true)
		{
			try
			{
				userChoice = menu.ShowMenuAndGetUserChoice();
				if(executeByUserChoide(userChoice)) //game finished
				{
					if(userChoice == 6 || userChoice == 8)
					{
						try
						{
							System.out.println("Game Finished!\n" +
							                   "Player " + GameManager.Instance().GetCurrentPlayer().GetPlayerNumber() + " Quit\n" +
							                   "Player " + ((GameManager.Instance().GetCurrentPlayer().GetPlayerNumber()+1) %2) + " WON!!!\n");
						}
						catch(Exception e)
						{
							if(userChoice == 8)
							{
								System.out.println("Exiting from game...");
								System.exit(0);
							}
						}
					}
					else
					{
						System.out.println("Game Finished!\n" +
						                   "Player " + GameManager.Instance().GetCurrentPlayer().GetPlayerNumber() + " WON!!!\n");
					}

					printPlayersBattleShipBoards();
					printGameStatistics();

					if(userChoice != 8)
					{
						System.out.println("Select 2 for start a new game");
						m_GameStarted = false;
					}
					else
					{
						System.out.println("Exiting from game...");
						System.exit(0);
					}
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}

	private static void printPlayersBattleShipBoards()
	{
		Player[] players = GameManager.Instance().GetAllPlayers();

		for (Player p : players)
		{
			System.out.println("Player " + p.GetPlayerNumber() + " board:");
			printBattleShipBoard(p);
		}

		System.out.println();
	}

	private static boolean executeByUserChoide(int userChoice) throws Exception
	{
		boolean isGameFinished = false;

		switch(userChoice)
		{
			case 1:
				loadGameSettings();
				break;
			case 2:
				startGame();
				break;
			case 3:
				printGameState();
				break;
			case 4:
				isGameFinished = makeMove();
				break;
			case 5:
				printGameStatistics();
				break;
			case 6:
				isGameFinished = true;
				break;
			case 7:
				setMine();
				break;
			case 8:
				isGameFinished = true;
				break;
		}

		return isGameFinished;
	}

	private static void printGameStatistics() throws Exception
	{
		if(m_GameStarted == false)
		{
			throw new Exception("Start game before select game statistics");
		}

		GameStatistics gameStats = GameManager.Instance().GetGameStatistics();

		System.out.println(gameStats.toString());

		for(Player p : GameManager.Instance().GetAllPlayers())
		{
			System.out.println(p.GetPlayerStatistics().toString());
		}
	}

	public static Point getPointFromUser(String i_For)
	{
		Scanner input = new Scanner(System.in);
		Point res = new Point();

		System.out.print("Please insert row for " + i_For + ": (1 - " + m_BoardSize + "): ");
		res.y = input.nextInt();
		System.out.println();
		System.out.print("Please insert column to "+ i_For + ": (A - " + (char)('A' + m_BoardSize - 1) + "): ");
		res.x = (int)(input.next().charAt(0) - 'A' + 1);

		return res;
	}

	private static void setMine() throws Exception
	{
		boolean isGameFinished = false;

		if(m_GameStarted == false)
		{
			throw new Exception("You can set mine after you start a game");
		}

		try
		{
			Point pointForMine = getPointFromUser("mine");
			GameManager.Instance().SetMine(GameManager.Instance().GetCurrentPlayer(), pointForMine);
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalArgumentException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Invalid point to set mine - Should be:\n" +
			                                   "Row: 1 - " + m_BoardSize + "\n" +
			                                   "Column: A - " + (char)('A' + m_BoardSize - 1) + "\n");
		}

	}

	private static boolean makeMove() throws Exception
	{
		boolean isGameFinished = false;

		Point pointToAttack = null;

		if(m_GameStarted == false)
		{
			throw new Exception("You can make move after you start a game");
		}

		try
		{
			Player currentPlayer = GameManager.Instance().GetCurrentPlayer();
			System.out.println("It's player " + currentPlayer.GetPlayerNumber() + " turn");
			printBattleShipBoard(currentPlayer);
			printTraceBoard(currentPlayer);
			pointToAttack = getPointFromUser("attack");
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Invalid point to attack - Should be:\n" +
			                                   "Row: 1 - " + m_BoardSize + "\n" +
			                                   "Column: A - " + (char)('A' + m_BoardSize - 1) + "\n");
		}

		Player attack = GameManager.Instance().GetCurrentPlayer();
		AttackResult attackedResult = attack.HitPoint((attack.GetPlayerNumber() + 1) % 2, pointToAttack); //In this UI only two players playing...
		if(attackedResult.GetBeforeAttackSign() == BoardSigns.BATTLE_SHIP)
		{
			System.out.println("Player " + attack.GetPlayerNumber() + " hit BattleShip!");
		}

		if(attackedResult.GetIsBattleShipDrawn() != null)
		{
			System.out.println("Player " + attack.GetPlayerNumber() + " drawn BattleShip!!!");
		}

		if(attackedResult.GetPlayerWon())
		{
			isGameFinished = true;
		}

		return isGameFinished;
	}

	private static void printGameState() throws Exception
	{
		if(m_GameStarted == false)
		{
			throw new Exception("You should start game before watch game status");
		}

		Player currentPlayer = GameManager.Instance().GetCurrentPlayer();
		System.out.println("It's player " + currentPlayer.GetPlayerNumber() + " turn");
		System.out.println("Score = " + currentPlayer.GetPlayerStatistics().GetPoints());
		printBattleShipBoard(currentPlayer);
		printTraceBoard(currentPlayer);
	}

	private static void printBattleShipBoard(Player i_Player)
	{
		System.out.println("BattleShip Board: ");
		printBoard(i_Player.GetBattleShipGameBoard());
		System.out.println();
	}

	private static void printTraceBoard(Player i_Player)
	{
		System.out.println("Trace Board: ");
		printBoard(i_Player.GetTraceGameBoard());
		System.out.println();
	}


	private static void startGame() throws Exception
	{
		if(m_GameStarted)
		{
			throw new Exception("Please finish the current game before start a new one");
		}

		if(m_XmlLoaded)
		{
			GameManager.Instance().InitGame();
			m_GameStarted = true;
			m_BoardSize = GameManager.Instance().GetCurrentPlayer().GetBattleShipGameBoard().GetBoardSize();
			System.out.println("Game Started!");
		}
		else
		{
			throw new Exception("Load game settings before starting new game");
		}
	}

	private static void printBoard(GameBoard i_GameBoard)
	{
		int size = m_BoardSize;
		BoardSigns[][] boardArr = i_GameBoard.GetBoard();
		int rowLen;

		//Print column numbers
		System.out.print(" ");
		rowLen = 1;
		for(int i = 0; i < size; ++i)
		{
			System.out.print(" | " + (char)('A' + i));
			rowLen += 4;
		}

		System.out.println(" |");
		rowLen += 2;
		printRowSeparator(rowLen);

		for(int i = 1; i <= size; i++)
		{
			//Print row number
			if(i < 10)
			{
				System.out.print(i + " |");
			}
			else
			{
				System.out.print(i + "|");
			}

			for(int j = 1; j <= size; ++j)
			{
				System.out.print(" " + boardArr[i][j].GetValue() + " |");
			}

			System.out.println();
			printRowSeparator(rowLen);
		}
	}

	private static void printRowSeparator(int rowLen)
	{
		for(int i = 0; i < rowLen; ++i)
		{
			System.out.print('-');
		}

		System.out.println();
	}

	private static void loadGameSettings() throws Exception
	{
		if(m_GameStarted == true)
		{
			throw new Exception("You can't load game settings after game started");
		}

		System.out.println("Please insert the xml path: ");
		Scanner input = new Scanner(System.in);
		String xmlPath = input.nextLine();

		GameManager.Instance().LoadGameSettings(xmlPath);
		System.out.println("Game settings loaded successfully!");
		m_XmlLoaded = true;
	}
}
