package BattleShipGameLogic;

import java.util.ArrayList;
import java.util.List;

public class AttackResult
{
	private BoardSigns m_SignBeforeAttack;
	private boolean m_IsBattleShipAttacked = false;
	private boolean m_IsBattleShipDrawn = false;
	private boolean m_IsMineAttacked = false;
	private boolean m_PlayerWon = false;
	private Player[] m_Players;
	private int m_AttackerPlayerIdx;
	private BattleShip m_BattleShip;
	private Mine m_Mine;
	private List<String> m_AttackInfo;

	public AttackResult(BoardSigns i_SignBeforeAttack)
	{
		m_AttackInfo = new ArrayList<>();
		m_SignBeforeAttack = i_SignBeforeAttack;
	}

	public List<String> GetAttackInfo()
	{
		return m_AttackInfo;
	}

	public void AddAttackInfo(String i_AttackInfo)
	{
		m_AttackInfo.add(0, i_AttackInfo);
	}

	public BattleShip GetIsBattleShipDrawn()
	{
		if(m_IsBattleShipDrawn)
		{
			return m_BattleShip;
		}

		return null;
	}

	public Player GetAttacker()
	{
		return m_Players[m_AttackerPlayerIdx];
	}

	public BattleShip GetIsBattleShipHit()
	{
		if(m_IsBattleShipAttacked)
		{
			return m_BattleShip;
		}

		return null;
	}

	public BoardSigns GetBeforeAttackSign()
	{
		return m_SignBeforeAttack;
	}

	public boolean GetPlayerWon()
	{
		return m_PlayerWon;
	}

	public void MarkPlayerWon()
	{
		m_PlayerWon = true;
	}

	public void MarkBattleShipDrawn(BattleShip i_DrawnBattleShip)
	{
		m_BattleShip = i_DrawnBattleShip;
		m_IsBattleShipDrawn = true;
	}

	public void MarkMineAttacked(Mine i_Mine)
	{
		m_Mine = i_Mine;
		m_IsMineAttacked = true;
	}

	public Mine GetMineAttacked()
	{
		if(m_IsMineAttacked)
		{
			return m_Mine;
		}

		return null;
	}

	public void MarkBattleShipAttacked(BattleShip i_AttackedBattleShip)
	{
		m_IsBattleShipAttacked = true;
		m_BattleShip = i_AttackedBattleShip;
	}

	public void SetPlayers(Player[] i_Players, int i_AttackerIdx)
	{
		m_Players = new Player[i_Players.length];

		for(int i = 0; i < i_Players.length; ++i)
		{
			m_Players[i] = i_Players[i].Clone();
		}

		m_AttackerPlayerIdx = i_AttackerIdx;
	}

	public Player GetAttacked()
	{
		int attackesIdx = m_AttackerPlayerIdx == 0 ? 1 : 0;

		return m_Players[attackesIdx];
	}
}
