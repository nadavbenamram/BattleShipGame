package BattleShipGameLogic;

public class PlayerStatistics
{
	private int m_Points;
	private int m_Miss;
	private int m_Hurt;

	public PlayerStatistics()
	{
		m_Points = 0;
		m_Miss = 0;
		m_Hurt = 0;
	}

	public void Hurt()
	{
		m_Hurt++;
		m_Points++; //Each hurt is point in the first exercise only.
	}

	public void Missed()
	{
		m_Miss++;
	}

	public int GetPoints()
	{
		return m_Points;
	}
}
