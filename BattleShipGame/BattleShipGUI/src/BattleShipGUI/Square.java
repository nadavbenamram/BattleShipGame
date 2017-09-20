package BattleShipGUI;

import BattleShipGameLogic.*;
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

import java.awt.*;

public class Square extends StackPane
{
	private BoardSigns m_Sign;
	private Rectangle m_Border;
	public static final int Size = 42;
	private int m_Row;
	private int m_Column;
	private ImageView m_Image;

	public ImageView GetImageView()
	{
		return m_Image;
	}

	public Rectangle GetBorder()
	{
		return m_Border;
	}

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

							BattleShip battleShip = attackResult.GetIsBattleShipDrawn();
							if(Utils.isGameFinished == false)
							{
								if(battleShip != null)
								{
									Player attacker = attackResult.GetAttacker();
									if(Main.ApproveAnimations)
									{
										Point[] shipPoints = battleShip.GetOriginalPoints();
										Main.GetTraceBoard().ScaleCells(shipPoints);
									}

									Main.showMessageBox("BattleShip drwan", "Player " + attacker.GetPlayerNumber() + " drawn BattleShip!!!", Alert.AlertType.INFORMATION);
								}
								else
								{
									battleShip = attackResult.GetIsBattleShipHit();
									if(battleShip != null)
									{
										Player attacker = attackResult.GetAttacker();
										if(Main.ApproveAnimations)
										{
											Main.GetTraceBoard().RotateCells(new Point[]{battleShip.GetLastActivePoint()});
										}
									}
									else
									{
										Mine mine = attackResult.GetMineAttacked();
										if(mine != null)
										{
											ImageView mineImage = new ImageView();
											Image image = new Image("BattleShipGUI/mine.png");
											mineImage.setImage(image);

											ImageView emptyImage = new ImageView();
											Image emptyimage = new Image("BattleShipGUI/empty.png");
											emptyImage.setImage(emptyimage);

											Main.GetTraceBoard().ChangeImageAndRotate(emptyImage, mineImage, new Point[]{mine.GetLocation()});
										}
									}
								}
							}
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

	public void SetNewImage(ImageView i_NewImage)
	{
		getChildren().removeAll(m_Image);
		getChildren().add(i_NewImage);
	}
}