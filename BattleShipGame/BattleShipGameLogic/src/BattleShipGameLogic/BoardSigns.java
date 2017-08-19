package BattleShipGameLogic;

public enum BoardSigns
{
	EMPTY  ('O'),
	BATTLE_SHIP ('V'),
	MINE ('*'),
	BATTLE_SHIP_HIT ('X'),
	HIT('H');

	BoardSigns(char i_Sign)
	{

	}
}