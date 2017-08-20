import BattleShipGameLogic.BoardSigns;
import BattleShipGameLogic.GameBoard;
import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.Player;
import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.util.Scanner;

public class UiManager
{
	private static boolean m_XmlLoaded = false;
	private static boolean m_GameStarted = false;

	public static void main(String[] args) throws IOException
	{
		Menu menu = new Menu();
		int userChoice;

		while (true)
		{
			try
			{
				userChoice = menu.ShowMenuAndGetUserChoice();
				executeByUserChoide(userChoice);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}

	private static void executeByUserChoide(int userChoice) throws Exception
	{
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
		}
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
		if(m_XmlLoaded)
		{
			GameManager.Instance().InitGame();
			m_GameStarted = true;
			System.out.println("Game Started!");
		}
		else
		{
			throw new Exception("Load game settings before starting new game");
		}
	}

	private static void printBoard(GameBoard i_GameBoard)
	{
		int size = i_GameBoard.GetBoardSize();
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
			System.out.print(i + " |");

			for(int j = 1; j <= size; ++j)
			{
				System.out.print(" " + boardArr[j][i].GetValue() + " |");
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
