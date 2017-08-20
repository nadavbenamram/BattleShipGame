package BattleShipGameLogic;

public enum BoardSigns
{
	EMPTY  ('O'),
	BATTLE_SHIP ('V'),
	MINE ('*'),
	BATTLE_SHIP_HIT ('X'),
	HIT('H');

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