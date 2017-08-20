import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu
{
	private static final String MENU_OPTIONS = "Your options are:\n" +
	                                    "1. Load game details from XML\n" +
	                                    "2. Start game\n" +
	                                    "3. Show game status\n" +
	                                    "4. Make turn\n" +
	                                    "5. Show game statistics\n" +
	                                    "6. Finish current game\n" +
	                                    "7. Put mine\n" +
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
		System.out.println();
		System.out.println(MENU_OPTIONS);
		System.out.println();
		System.out.print("Your choice: ");
	}

	private int getChoiceFromUser() throws IOException
	{
		int choice = 0;
		boolean firstIter = true;
		Scanner input = new Scanner(System.in);

		do
		{
			if(!firstIter)
			{
				System.out.println("Invalid choice! values can be between 1 to 8 only!");
			}

			printMenu();
			try
			{
				choice = input.nextInt();
				firstIter = false;
			}
			catch (InputMismatchException e)
			{
				System.out.println("Invalid choice format! values can be only numbers!");
				firstIter = true;
				choice = 0;
				input.nextLine();
			}
		}
		while(choice < 1 || choice > 8);

		return choice;
	}
}
