package JsonObjects;

import Utils.User;

import java.util.List;

public class GameJson
{
	private String Title;
	private String Owner;
	private int BoardSize;
	private String GameType;
	private int ActivePlayersNum;
	private GameStatisticsJson GameStatistcs;
	private UserJson CurrentPlayer;
	private boolean IsStarted;
	private List<String> UserNameChat;
	private List<String> MessageChat;
	private int NumOfMessage;

	public List<String> getM_UserNameChat()
	{
		return UserNameChat;
	}

	public void setM_UserNameChat(List<String> m_UserNameChat)
	{
		this.UserNameChat = m_UserNameChat;
	}

	public List<String> getM_MessageChat()
	{
		return MessageChat;
	}

	public void setM_MessageChat(List<String> m_MessageChat)
	{
		this.MessageChat = m_MessageChat;
	}

	public boolean isStarted()
	{
		return IsStarted;
	}

	public void setStarted(boolean started)
	{
		IsStarted = started;
	}

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

	public int getNumOfMessage()
	{
		return NumOfMessage;
	}

	public void setNumOfMessage(int numOfMessage)
	{
		NumOfMessage = numOfMessage;
	}
}
