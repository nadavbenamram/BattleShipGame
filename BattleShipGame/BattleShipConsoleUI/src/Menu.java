import java.io.IOException;

public class Menu
{
	private static final String m_Menu= "Your options are:" +
	                                    "1. Load game details from XML" +
	                                    "2. Start game" +
	                                    "3. Show game status" +
	                                    "4. Make turn" +
	                                    "5. Show game statistics" +
	                                    "6. Finish current game" +
	                                    "7. Put mine" +
	                                    "8. Exit game";

	public Menu()
	{
	}

	public int ShowMenuAndGetUserChoice() throws IOException
	{
		return getChoiceFromUser();
	}

	private void printMenu()
	{
		System.out.println(m_Menu);
	}

	private int getChoiceFromUser() throws IOException
	{
		int choice;
		boolean firstIter = true;

		do
		{
			printMenu();

			if(!firstIter)
			{
				System.out.println();
				System.out.println("Invalid choice! values can be between 1 to 8 only!");
			}

			System.out.println();
			System.out.println("Please enter your choice:");
			choice = System.in.read();
			firstIter = false;
		}
		while(choice < 1 || choice > 8);

		return choice;
	}
}
