package BattleShipGUI;

import BattleShipGameLogic.BoardSigns;
import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.Player;
import generated.BattleShipGame;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GameBoard
{
	private int m_BoardSize;
	private GridPane m_Pane;
	private List<Square> m_PlayerGameBoard;

	public GameBoard(GridPane i_GridPane)
	{
		m_Pane = i_GridPane;
		m_BoardSize = GameManager.Instance().GetBoardSize();
		m_PlayerGameBoard = new ArrayList<>();
		init();
	}

	public Pane GetBoard()
	{
		return m_Pane;
	}

	private void init()
	{
		int x = 0;
		int y = 0;

		Square s = new Square(' ', 0, 0);
		m_Pane.add(s, 0, 0);

		for(int i = 1; i <= m_BoardSize; ++i)
		{
			s = new Square((char)('A' + i - 1), 0, i);
			m_Pane.add(s, i, 0);
		}

		for(int i = 1; i <= m_BoardSize; ++i)
		{
			s = new Square(String.valueOf(i), i, 0);
			m_Pane.add(s, 0, i);

			for(int j = 1; j <= m_BoardSize; ++j)
			{
				s = new Square(BoardSigns.EMPTY, i, j);
				m_PlayerGameBoard.add(s);
				m_Pane.add(s, j, i);
			}
		}
	}

	public void UpdateContent(BattleShipGameLogic.GameBoard i_GameBoard, boolean i_IsClickable)
	{
		BoardSigns[][] board = i_GameBoard.GetBoard();
		int idx = 0;

		for(int i = 1; i <= m_BoardSize; ++i)
		{
			for(int j = 1; j <= m_BoardSize; ++j)
			{
				Square s = m_PlayerGameBoard.get(idx++);
				s.UpdateSign(board[i][j], i_IsClickable);
			}
		}
	}
}


