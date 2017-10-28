package JsonObjects;

import Utils.User;

public class GameJson
{
	private String Title;
	private String Owner;
	private int BoardSize;
	private String GameType;
	private int ActivePlayersNum;
	private GameStatisticsJson GameStatistcs;
	private UserJson CurrentPlayer;

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getOwner()
	{
		return Owner;
	}

	public void setOwner(String owner)
	{
		Owner = owner;
	}

	public int getBoardSize()
	{
		return BoardSize;
	}

	public void setBoardSize(int boardSize)
	{
		BoardSize = boardSize;
	}

	public String getGameType()
	{
		return GameType;
	}

	public void setGameType(String gameType)
	{
		GameType = gameType;
	}

	public int getActivePlayersNum()
	{
		return ActivePlayersNum;
	}

	public void setActivePlayersNum(int activePlayersNum)
	{
		ActivePlayersNum = activePlayersNum;
	}

	public GameStatisticsJson getGameStatistcs()
	{
		return GameStatistcs;
	}

	public void setGameStatistcs(GameStatisticsJson gameStatistcs)
	{
		GameStatistcs = gameStatistcs;
	}

	public UserJson getCurrentPlayer()
	{
		return CurrentPlayer;
	}

	public void setCurrentPlayer(UserJson currentPlayer)
	{
		CurrentPlayer = currentPlayer;
	}
}
