import BattleShipGameLogic.GameManager;

import java.io.IOException;

public class UiManager
{
	public static void main(String[] args) throws IOException
	{
		Menu menu = new Menu();
		int userChoice;

		while (true)
		{
			userChoice = menu.ShowMenuAndGetUserChoice();
		}
	}
}
