package BattleShipGameLogic;

public class Stopwatch
{
	private volatile long m_StartTime;

	public Stopwatch(long i_StartTime)
	{
		m_StartTime = i_StartTime;
	}

	public void ZeroTimer()
	{
		m_StartTime = System.currentTimeMillis();
	}

	public String getTime()
	{
		long milliTime = System.currentTimeMillis() - m_StartTime;
		int[] out = new int[]{0, 0, 0, 0};
		out[0] = (int)(milliTime / 3600000      );
		out[1] = (int)(milliTime / 60000        );
		out[2] = (int)(milliTime / 1000         ) % 60;
		out[3] = (int)(milliTime)                 % 1000;

		return out[1] + ":" + out[2];
	}

	public long GetTimeInSeconds()
	{
		Player p = GameManager.Instance().GetAllPlayers()[0];
		long test = System.currentTimeMillis();
		long milliTime = test - m_StartTime;
		return milliTime / 1000;
	}
}
