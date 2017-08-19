package BattleShipGameLogic;

public class Stopwatch extends Thread
{
	private long startTime;
	private boolean started;

	public void startThread()
	{
		this.startTime = System.currentTimeMillis();
		this.started = true;
		this.start();
	}

	public void run()
	{
		while (started)
		{
			// empty code since currentTimeMillis increases by itself
		}
	}


	public String getTime()
	{
		long milliTime = System.currentTimeMillis() - this.startTime;
		int[] out = new int[]{0, 0, 0, 0};
		out[0] = (int)(milliTime / 3600000      );
		out[1] = (int)(milliTime / 60000        ) % 60;
		out[2] = (int)(milliTime / 1000         ) % 60;
		out[3] = (int)(milliTime)                 % 1000;

		return out[1] + ":" + out[2];
	}

	public void stopThread()
	{
		this.started = false;
	}
}
