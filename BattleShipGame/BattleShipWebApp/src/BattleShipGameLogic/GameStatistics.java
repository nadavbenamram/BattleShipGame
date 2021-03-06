package BattleShipGameLogic;

public class GameStatistics
{
	private int m_Steps;
	private Stopwatch m_StopWatch;

	public GameStatistics()
	{
		m_Steps = 0;
		m_StopWatch = new Stopwatch(System.currentTimeMillis());
	}

	public String GetGameDuration()
	{
		return m_StopWatch.getTime();
	}

	public int GetSteps()
	{
		return m_Steps;
	}

	public void AddStep()
	{
		m_Steps++;
	}

	@Override
	public String toString()
	{
		return "Game Statitsics:\n" +
		       "Number of Steps: " + m_Steps + "\n" +
		       "Game Duration: " + GetGameDuration();
	}
}
