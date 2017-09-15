package BattleShipGameLogic;

public class PlayerStatistics
{
	private int m_PlayerIdx;
	private int m_Points;
	private int m_Miss;
	private int m_Hit;
	private long m_AverageStepTime; //seconds

	public PlayerStatistics()
	{}

	public PlayerStatistics(int i_PlayerIdx)
	{
		m_PlayerIdx = i_PlayerIdx;
		m_Points = 0;
		m_Miss = 0;
		m_Hit = 0;
		m_AverageStepTime = 0;
	}

	public PlayerStatistics Clone()
	{
		PlayerStatistics res = new PlayerStatistics();

		res.m_PlayerIdx = m_PlayerIdx;
		res.m_Points = m_Points;
		res.m_Miss = m_Miss;
		res.m_Hit = m_Hit;
		res.m_AverageStepTime = m_AverageStepTime;

		return res;
	}

	public int TotalSteps()
	{
		return m_Miss + m_Hit;
	}

	public void AddPoints(int i_Points)
	{
		m_Points += i_Points;
	}

	public void Hit(long i_StepTime)
	{
		updateAverageStepTime(i_StepTime);
		m_Hit++;
	}

	public void Missed(long i_StepTime)
	{
		updateAverageStepTime(i_StepTime);
		m_Miss++;
	}

	public int GetPoints()
	{
		return m_Points;
	}

	private void updateAverageStepTime(long i_StepTime)
	{
		if(m_AverageStepTime == 0)
		{
			m_AverageStepTime = i_StepTime;
		}
		else
		{
			m_AverageStepTime = (m_AverageStepTime * TotalSteps() + i_StepTime) / (TotalSteps() + 1);
		}
	}

	public long GetAvgStepTimeInSeconds()
	{
		return m_AverageStepTime;
	}

	@Override
	public String toString()
	{
		return "Player " + m_PlayerIdx + " Statistics:\n" +
		       "Score: " + m_Points + "\n" +
		       "Miss: " + m_Miss + "\n" +
		       "Average time for making move: " + GetAvgStepTimeInSeconds() + " seconds";
	}
}
