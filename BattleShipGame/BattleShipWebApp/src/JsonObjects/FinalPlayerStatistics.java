package JsonObjects;

public class FinalPlayerStatistics
{
	private String PlayerName;
	private int Points;
	private int Miss;
	private int Hit;
	private long AverageStepTime;

	public String getPlayerName()
	{
		return PlayerName;
	}

	public void setPlayerName(String playerName)
	{
		PlayerName = playerName;
	}

	public int getPoints()
	{
		return Points;
	}

	public void setPoints(int points)
	{
		Points = points;
	}

	public int getMiss()
	{
		return Miss;
	}

	public void setMiss(int miss)
	{
		Miss = miss;
	}

	public int getHit()
	{
		return Hit;
	}

	public void setHit(int hit)
	{
		Hit = hit;
	}

	public long getAverageStepTime()
	{
		return AverageStepTime;
	}

	public void setAverageStepTime(long averageStepTime)
	{
		AverageStepTime = averageStepTime;
	}
}
