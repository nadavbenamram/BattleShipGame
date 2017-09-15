package BattleShipGameLogic;

public enum BoardSigns
{
	EMPTY  (' '),
	BATTLE_SHIP ('@'),
	MINE ('*'),
	BATTLE_SHIP_HIT ('X'),
	HIT('#');

	private char m_Char;

	BoardSigns(char i_Sign)
	{
		m_Char = i_Sign;
	}

	public char GetValue()
	{
		return m_Char;
	}
}