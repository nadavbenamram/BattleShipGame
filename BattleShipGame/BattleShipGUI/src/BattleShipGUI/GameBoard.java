package BattleShipGUI;

import BattleShipGameLogic.BoardSigns;
import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.Player;
import generated.BattleShipGame;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;

import java.awt.*;
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

	public void RotateCells(Point[] i_Cells)
	{
		int column;
		int row;
		int idx;

		for(int i = 0; i < i_Cells.length; ++i)
		{
			column = i_Cells[i].x - 1;
			row = i_Cells[i].y - 1;
			idx = (row * m_BoardSize) + column;
			Square square = m_PlayerGameBoard.get(idx);
			RotateTransition rt = new RotateTransition(Duration.millis(1000), square.GetBorder());
			rt.setByAngle(360);
			rt.setCycleCount(1);
			rt.setAutoReverse(true);
			rt.setNode(square);
			rt.play();
		}

	}

	public void ScaleCells(Point[] i_Cells)
	{
		int column;
		int row;
		int idx;

		for(int i = 0; i < i_Cells.length; ++i)
		{
			column = i_Cells[i].x - 1;
			row = i_Cells[i].y - 1;
			idx = (row * m_BoardSize) + column;
			Square square = m_PlayerGameBoard.get(idx);

			FadeTransition ft = new FadeTransition(Duration.millis(800), square.GetBorder());
			ft.setFromValue(1.0);
			ft.setToValue(0.3);
			ft.setCycleCount(4);
			ft.setAutoReverse(true);
			ft.setNode(square);
			ft.play();
		}
	}

	public void ChangeImageAndRotate(ImageView i_Org, ImageView i_Tmp, Point[] i_Cells) throws InterruptedException
	{
		int column;
		int row;
		int idx;

		for(int i = 0; i < i_Cells.length; ++i)
		{
			column = i_Cells[i].x - 1;
			row = i_Cells[i].y - 1;
			idx = (row * m_BoardSize) + column;
			Square square = m_PlayerGameBoard.get(idx);
			square.SetNewImage(i_Tmp);

			RotateTransition rt = new RotateTransition(Duration.millis(1000), square.GetBorder());
			rt.setByAngle(360);
			rt.setCycleCount(2);
			rt.setAutoReverse(true);
			rt.setNode(square);
			rt.setOnFinished(event -> {square.SetNewImage(i_Org);});
			rt.play();
		}
	}
}


