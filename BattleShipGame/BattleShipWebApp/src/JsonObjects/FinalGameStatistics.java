package JsonObjects;

import Utils.Constants;

public class FinalGameStatistics
{
	private int TotalSteps;
	private String GameDuration;
	private boolean IsPlayerLeft = false;
	private String PlayerLeftName;
	private FinalPlayerStatistics PlayersStatistics[];

	public FinalGameStatistics()
	{
		PlayersStatistics = new FinalPlayerStatistics[Constants.PLAYERS_NUM_PER_NAME];
	}

	public int getTotalSteps()
	{
		return TotalSteps;
	}

	public void setTotalSteps(int totalSteps)
	{
		TotalSteps = totalSteps;
	}

	public String getGameDuration()
	{
		return GameDuration;
	}

	public void setGameDuration(String gameDuration)
	{
		GameDuration = gameDuration;
	}

	public FinalPlayerStatistics[] getPlayersStatistics()
	{
		return PlayersStatistics;
	}

	public void setPlayersStatistics(FinalPlayerStatistics[] playersStatistics)
	{
		PlayersStatistics = playersStatistics;
	}

	public boolean isPlayerLeft()
	{
		return IsPlayerLeft;
	}

	public void setPlayerLeft(boolean playerLeft)
	{
		IsPlayerLeft = playerLeft;
	}

	public String getPlayerLeftName()
	{
		return PlayerLeftName;
	}

	public void setPlayerLeftName(String playerLeftName)
	{
		PlayerLeftName = playerLeftName;
	}
}
