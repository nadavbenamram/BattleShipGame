package BattleShipGameLogic;

public class AttackResult
{
	private BoardSigns m_SignBeforeAttack;
	private boolean m_IsBattleShipAttacked = false;
	private boolean m_IsBattleShipDrawn = false;
	private boolean m_IsMineAttacked = false;
	private boolean m_PlayerWon = false;
	private Player m_AttackerPlayer;

	public AttackResult(BoardSigns i_SignBeforeAttack)
	{
		m_SignBeforeAttack = i_SignBeforeAttack;
	}

	public BoardSigns GetBeforeAttackSign()
	{
		return m_SignBeforeAttack;
	}

	public boolean GetIsBattleShipDrawn()
	{
		return m_IsBattleShipDrawn;
	}

	public boolean GetPlayerWon()
	{
		return m_PlayerWon;
	}

	public void MarkPlayerWon()
	{
		m_PlayerWon = true;
	}

	public void MarkBattleShipDrawn()
	{
		m_IsBattleShipDrawn = true;
	}

	public void MarkMineAttacked()
	{
		m_IsMineAttacked = true;
	}

	public void MarkBattleShipAttacked()
	{
		m_IsBattleShipAttacked = true;
	}

	public void SetAttacker(Player i_Attacker)
	{
		m_AttackerPlayer = i_Attacker;
	}
}
