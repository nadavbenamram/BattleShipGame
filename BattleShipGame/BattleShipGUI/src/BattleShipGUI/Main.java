package BattleShipGUI;

import BattleShipGameLogic.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends Application {

	private static boolean m_IsXmlLoaded = false;
	private static BorderPane m_Root = new BorderPane();
	private static GameBoard m_PlayerGameBoard;
	private static GameBoard m_PlayerTraceBoard;
	private static Stage m_PrimaryStage;

	@Override
	public void start(Stage primaryStage) {
		m_PrimaryStage = primaryStage;
		Scene scene = new Scene(m_Root, 1200, 600, Color.WHITE);
		Text test = new Text();
		m_Root.setCenter((test));
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		menuBar.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		m_Root.setTop(menuBar);

		// File menu - new, save, print, and exit
		Menu optionsMenu = new Menu("Options");
		MenuItem loadXmlMenuItem = new MenuItem("Load xml");
		MenuItem startNewGameMenuItem = new MenuItem("Start New Game");
		MenuItem finishCurrentGameMenuItem = new MenuItem("Finish Current Game");
		MenuItem exitMenuItem = new MenuItem("Exit");

		//add the new menuitems to the fileMenu
		optionsMenu.getItems().addAll(loadXmlMenuItem, startNewGameMenuItem, finishCurrentGameMenuItem, new SeparatorMenuItem(), exitMenuItem);
		menuBar.getMenus().addAll(optionsMenu);

		loadXmlMenuItem.setOnAction(event -> {
			try
			{
				setGameSettings(primaryStage);
				showMessageBox("Game settings has been Loaded!", "Game settings has been Loaded!\nYou can start the game now", Alert.AlertType.INFORMATION);
			}
			catch(Exception e)
			{
				showMessageBox("Error!", "Error on loading xml!", Alert.AlertType.ERROR);
			}
		});

		startNewGameMenuItem.setOnAction(event -> {
			try
			{
				doWhenStartNewGame(primaryStage);
			}
			catch (Exception e)
			{
				showMessageBox("Can't start game", e.getMessage(), Alert.AlertType.ERROR);
			}
		});

		finishCurrentGameMenuItem.setOnAction(event -> {

		});

		exitMenuItem.setOnAction(actionEvent-> Platform.exit());

		primaryStage.setTitle("Adding Menus");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private static Node gameStatisticsDetails()
	{
		VBox gameStatisticsDetails = new VBox();

		gameStatisticsDetails.setPadding(new Insets(5, 5, 5, 0));
		Player currentPlayer = GameManager.Instance().GetCurrentPlayer();
		Player[] allPlayers = GameManager.Instance().GetAllPlayers();
		GameStatistics gameStatistics = GameManager.Instance().GetGameStatistics();

		Text blankLine = new Text("");

		gameStatisticsDetails.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Text currentUserDetailsTitle = new Text("Game Statistics:");
		currentUserDetailsTitle.setUnderline(true);
		currentUserDetailsTitle.setFont(Font.font(16));
		Text movesNumber = new Text("Move Number: " + (gameStatistics.GetSteps() + 1));
		movesNumber.setFont(Font.font(14));

		gameStatisticsDetails.getChildren().addAll(currentUserDetailsTitle, blankLine, movesNumber, new Separator());

		Text currentPlayerId = new Text("Current Player Id: " + currentPlayer.GetPlayerNumber());
		currentPlayerId.setFont(Font.font(14));
		gameStatisticsDetails.getChildren().addAll(currentPlayerId, new Separator());

		for(Player player : allPlayers)
		{
			if(player.GetPlayerNumber() == currentPlayer.GetPlayerNumber())
			{
				continue;
			}

			Text battleShipsList = new Text("Player " + player.GetPlayerNumber() + " BattleShips list: ");
			battleShipsList.setFont(Font.font(14));
			gameStatisticsDetails.getChildren().addAll(battleShipsList);
			Map<Integer, Integer> dict = getPlayerBattleShipsList(GameManager.Instance().GetCurrentPlayer());
			for (Integer length : dict.keySet())
			{
				Text text = new Text("Length " + length.toString() + ": " + dict.get(length));
				gameStatisticsDetails.getChildren().add(text);
			}

			Text playerScore = new Text("Player " + player.GetPlayerNumber() + " Score: " + player.GetPlayerStatistics().GetPoints());
			playerScore.setFont(Font.font(14));
			gameStatisticsDetails.getChildren().addAll(playerScore);

			gameStatisticsDetails.getChildren().addAll(new Separator());
		}

		return gameStatisticsDetails;
	}

	private static Node currentUserToolbox()
	{
		VBox currentUserDetails = new VBox();

		currentUserDetails.setPadding(new Insets(5, 5, 5, 0));
		Player currentPlayer = GameManager.Instance().GetCurrentPlayer();

		Text blankLine = new Text("");

		currentUserDetails.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Text currentUserDetailsTitle = new Text("Current Player Toolbox");
		currentUserDetailsTitle.setUnderline(true);
		currentUserDetailsTitle.setFont(Font.font(16));
		Text currentUserId = new Text("Player ID: " + currentPlayer.GetPlayerNumber());
		currentUserId.setFont(Font.font(14));

		currentUserDetails.getChildren().addAll(currentUserDetailsTitle, blankLine, currentUserId, new Separator());

		Text battleShipsList = new Text("BattleShips list: ");
		battleShipsList.setFont(Font.font(14));
		currentUserDetails.getChildren().addAll(battleShipsList);
		Map<Integer, Integer> dict = getPlayerBattleShipsList(GameManager.Instance().GetCurrentPlayer());
		for (Integer length : dict.keySet())
		{
			Text text = new Text("Length " + length.toString() + ": " + dict.get(length));
			currentUserDetails.getChildren().add(text);
		}

		Text opponenetBattleShipsList = new Text("Opponent BattleShips list: ");
		opponenetBattleShipsList.setFont(Font.font(14));
		currentUserDetails.getChildren().addAll(new Separator(), opponenetBattleShipsList);
		dict = getPlayerBattleShipsList(GameManager.Instance().GetAllPlayers()[(GameManager.Instance().GetCurrentPlayer().GetPlayerNumber() == 0) ? 1 : 0]);
		for (Integer length : dict.keySet())
		{
			Text text = new Text("Length " + length.toString() + ": " + dict.get(length));
			currentUserDetails.getChildren().add(text);
		}

		Text points = new Text("Points: " + currentPlayer.GetPlayerStatistics().GetPoints());
		points.setFont(Font.font(14));
		Text hits = new Text("Hits: " + currentPlayer.GetPlayerStatistics().GetHits());
		hits.setFont(Font.font(14));
		Text misses = new Text("Missed: " + currentPlayer.GetPlayerStatistics().GetMisses());
		misses.setFont(Font.font(14));
		Text avgMoveTime = new Text("Average move time: " + currentPlayer.GetPlayerStatistics().GetAvgStepTimeInSeconds());
		avgMoveTime.setFont(Font.font(14));

		Image tempMine = new Image("BattleShipGUI/mine.png");
		ImageView imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setImage(tempMine);
		final GridPane target = m_GameBoardGirdPane;

		imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
				ClipboardContent cbContent = new ClipboardContent();
				cbContent.putImage(imageView.getImage());
				db.setContent(cbContent);
				event.consume();
			}
		});

		target.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if(event.getGestureSource() != target && event.getDragboard().hasImage()){
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();
			}
		});

		target.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if(event.getGestureSource() != target && event.getDragboard().hasImage()){
					imageView.setVisible(false);
					target.setOpacity(0.7);
				}
				event.consume();
			}
		});

		target.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				imageView.setVisible(true);
				target.setOpacity(1);
				event.consume();
			}
		});

		target.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event){
				Dragboard db = event.getDragboard();
				boolean success = false;
				if(db.hasImage()){
					success = true;
					Point point = getColAndRowInGameBoardGrid(event.getX(), event.getY());
					try
					{
						setMine(point);
						SetBeforeMove();
					}
					catch (Exception e)
					{
						showMessageBox("Mine set Error!", e.getMessage(), Alert.AlertType.ERROR);
					}
				}

				event.setDropCompleted(success);
				event.consume();
			}
		});

		currentUserDetails.getChildren().addAll(new Separator(), points, new Separator(), hits, new Separator(), misses,
												new Separator(), avgMoveTime, new Separator(), imageView);

		return currentUserDetails;
	}

	private static void setMine(Point i_Point) throws Exception
	{
		boolean isGameFinished = false;

		if(Utils.isGameStarted == false)
		{
			throw new Exception("You can set mine after you start a game");
		}

		try
		{
			GameManager.Instance().SetMine(GameManager.Instance().GetCurrentPlayer(), i_Point);
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalArgumentException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Invalid point to set mine - Should be:\n" +
			                                   "Row: 1 - " + GameManager.Instance().GetBoardSize() + "\n" +
			                                   "Column: A - " + (char)('A' + GameManager.Instance().GetBoardSize() - 1) + "\n");
		}

	}

	private static Point getColAndRowInGameBoardGrid(double x, double y)
	{
		Point resPoint = new Point();

		resPoint.x = (int)(x / Square.Size);
		resPoint.y = (int)(y / Square.Size);

		return resPoint;
	}

	private static Map<Integer, Integer> getPlayerBattleShipsList(Player i_Player)
	{
		ArrayList<BattleShip> allBattleShips = i_Player.GetBattleShipGameBoard().GetBattleShip();

		Map<Integer, Integer> categoryDict = new HashMap<Integer, Integer>();
		for(BattleShip battleShip : allBattleShips)
		{
			if(categoryDict.containsKey(battleShip.GetLength()))
			{
				categoryDict.put(battleShip.GetLength(), categoryDict.get(battleShip.GetLength()) + 1);
			}
			else
			{
				categoryDict.put(battleShip.GetLength(), 1);
			}
		}

		return categoryDict;
	}

	private void doWhenStartNewGame(Stage primaryStage) throws Exception
	{
		if(m_IsXmlLoaded == false)
		{
			throw new Exception("You have to load xml before starts new game");
		}

		if(Utils.isGameStarted == true)
		{
			throw new Exception("Finish the current game before starts a new one");
		}

		GameManager.Instance().InitGame();
		Utils.isGameStarted = true;
		showMessageBox("Game Started!", "Game Started!", Alert.AlertType.INFORMATION);
		initBoards();
		SetBeforeMove();
	}

	private static GridPane m_GameBoardGirdPane = new GridPane();

	private void initBoards()
	{
		ScrollPane scrollPane = new ScrollPane();
		VBox gameBoard = new VBox();
		Text gameBoardTitle = new Text("Game Board");
		gameBoardTitle.setFont(Font.font(20));
		gameBoardTitle.setUnderline(true);
		m_PlayerGameBoard = new GameBoard(m_GameBoardGirdPane);
		gameBoard.setAlignment(Pos.CENTER);
		gameBoard.getChildren().addAll(gameBoardTitle, m_GameBoardGirdPane);

		VBox traceBoard = new VBox();
		Text traceBoardTitle = new Text("Trace Board");
		traceBoardTitle.setFont(Font.font(20));
		traceBoardTitle.setUnderline(true);
		GridPane traceBoardgrGridPane = new GridPane();
		m_PlayerTraceBoard = new GameBoard(traceBoardgrGridPane);
		traceBoard.setAlignment(Pos.CENTER);
		traceBoard.getChildren().addAll(traceBoardTitle, traceBoardgrGridPane);

		HBox centerPane = new HBox();
		centerPane.getChildren().addAll(gameBoard, new Separator(), traceBoard);
		scrollPane.setContent(centerPane);
		m_Root.setCenter(scrollPane);
	}

	public static void SetBeforeMove()
	{
		m_Root.setLeft(currentUserToolbox());
		m_Root.setRight(gameStatisticsDetails());
		m_PlayerTraceBoard.UpdateContent(GameManager.Instance().GetCurrentPlayer().GetTraceGameBoard(), true);
		m_PlayerGameBoard.UpdateContent(GameManager.Instance().GetCurrentPlayer().GetBattleShipGameBoard(), false);
	}

	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		// Category in column 2, row 1
		Text category = new Text("Sales:");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 1, 0);

		// Title in column 3, row 1
		Text chartTitle = new Text("Current Year");
		chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(chartTitle, 2, 0);

		// Subtitle in columns 2-3, row 2
		Text chartSubtitle = new Text("Goods and Services");
		grid.add(chartSubtitle, 1, 1, 2, 1);

		return grid;
	}

	public static void showMessageBox(String i_Title, String i_Message, Alert.AlertType i_AlertType) {
		Alert alert = new Alert(i_AlertType);
		alert.setTitle(i_Title);
		alert.setHeaderText(i_Title);
		alert.setContentText(i_Message);
		alert.showAndWait();
	}

	private void setGameSettings(Stage i_Stage) throws Exception
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open game settings xml");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Game settings (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File xmlFile = fileChooser.showOpenDialog(i_Stage);
		if(xmlFile != null)
		{
			GameManager.Instance().LoadGameSettings(xmlFile.getPath());
			m_IsXmlLoaded = true;
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
