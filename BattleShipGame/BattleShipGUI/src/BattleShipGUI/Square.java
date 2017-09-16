package BattleShipGUI;

import BattleShipGameLogic.AttackResult;
import BattleShipGameLogic.BoardSigns;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Square extends StackPane
{
	private BoardSigns m_Sign;
	private Rectangle m_Border;
	public static final int Size = 42;
	private int m_Row;
	private int m_Column;
	private ImageView m_Image;

	public Square(BoardSigns i_Sign, int i_Row, int i_Column)
	{
		m_Sign = i_Sign;
		m_Border = new Rectangle(Size, Size);
		setAlignment(Pos.CENTER);
		m_Border.setStroke(Color.BLACK);
		m_Border.setFill(null);
		getChildren().add(m_Border);
		m_Row = i_Row;
		m_Column = i_Column;
	}

	public Square(char i_Text, int i_Row, int i_Column)
	{
		m_Border = new Rectangle(Size, Size);
		setAlignment(Pos.CENTER);
		m_Border.setStroke(Color.BLACK);
		m_Border.setFill(null);
		getChildren().add(m_Border);
		m_Row = i_Row;
		m_Column = i_Column;
		Text text = new Text(String.valueOf(i_Text));
		getChildren().add(text);
	}

	public Square(String i_Text, int i_Row, int i_Column)
	{
		m_Border = new Rectangle(Size, Size);
		setAlignment(Pos.CENTER);
		m_Border.setStroke(Color.BLACK);
		m_Border.setFill(null);
		getChildren().add(m_Border);
		m_Row = i_Row;
		m_Column = i_Column;
		Text text = new Text(i_Text);
		getChildren().add(text);
	}

	public void UpdateSign(BoardSigns i_Sign, boolean i_IsClickable)
	{
		m_Sign = i_Sign;

		switch(m_Sign)
		{
			case MINE:
			{
				Image image = new Image("BattleShipGUI/mine.png");
				m_Image = new ImageView();
				m_Image.setImage(image);
				break;
			}

			case HIT:
			{
				Image image = new Image("BattleShipGUI/hit.png");
				m_Image = new ImageView();
				m_Image.setImage(image);
				break;
			}

			case BATTLE_SHIP:
			{
				Image image = new Image("BattleShipGUI/battleShip.png");
				m_Image = new ImageView();
				m_Image.setImage(image);
				break;
			}

			case BATTLE_SHIP_HIT:
			{
				Image image = new Image("BattleShipGUI/battleShipHit.png");
				m_Image = new ImageView();
				m_Image.setImage(image);
				break;
			}

			case EMPTY:
			{
				Image image = new Image("BattleShipGUI/empty.png");
				m_Image = new ImageView();
				m_Image.setImage(image);
				break;
			}
		}

		if(m_Image != null)
		{
			if(i_IsClickable)
			{
				m_Image.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						try
						{
							AttackResult attackResult = Utils.MakeMove(m_Row, m_Column);
							Main.SetBeforeMove();
						}
						catch (Exception e)
						{
							Main.showMessageBox("Move Error!", e.getMessage(), Alert.AlertType.ERROR);
						}
						event.consume();
					}
				});
			}

			getChildren().add(m_Image);
		}
	}
}