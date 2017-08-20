package BattleShipGameLogic;

public class PlayerStatistics
{
	private int m_PlayerIdx;
	private int m_Points;
	private int m_Miss;
	private int m_Hit;
	private long m_AverageStepTime; //seconds

	public PlayerStatistics(int i_PlayerIdx)
	{
		m_PlayerIdx = i_PlayerIdx;
		m_Points = 0;
		m_Miss = 0;
		m_Hit = 0;
		m_AverageStepTime = 0;
	}

	private int totalSteps()
	{
		return m_Miss + m_Hit;
	}

	public void Hit(long i_StepTime)
	{
		updateAverageStepTime(i_StepTime);
		m_Hit++;
		m_Points++; //Each hurt is point in the first exercise only.
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
			m_AverageStepTime = (m_AverageStepTime * totalSteps() + i_StepTime) / (totalSteps() + 1);
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
