package BattleShipGameLogic;

import generated.BattleShipGame;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard
{
	protected int m_BoardSize;
	protected BoardSigns[][] m_Board;
	protected final int BOARD_MIN_SIZE = 5;
	protected final int BOARD_MAX_SIZE = 20;

	public GameBoard(int i_BoardSize)
	{
		initBoard(i_BoardSize);
	}

	private void initBoard(int i_BoardSize)
	{
		m_BoardSize = i_BoardSize;
		if(m_BoardSize < BOARD_MIN_SIZE || m_BoardSize > BOARD_MAX_SIZE)
		{
			throw new IllegalArgumentException("Board size should be from: " + BOARD_MIN_SIZE + " to: " + BOARD_MAX_SIZE);
		}

		m_Board = new BoardSigns[m_BoardSize][m_BoardSize];
		for (BoardSigns[] row : m_Board)
		{
			Arrays.fill(row, BoardSigns.EMPTY);
		}
	}

	public BoardSigns[][] GetBoard()
	{
		return m_Board;
	}
}
