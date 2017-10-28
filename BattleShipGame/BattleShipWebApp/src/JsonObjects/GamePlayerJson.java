package JsonObjects;

import BattleShipGameLogic.BoardSigns;
import BattleShipGameLogic.Player;
import BattleShipGameLogic.PlayerStatistics;
import Utils.ContextManager;
import Utils.Game;
import Utils.SessionManager;
import com.google.gson.Gson;

public class GamePlayerJson
{
	private GameJson GameJson;
	private BoardSigns[][] GameBoard;
	private BoardSigns[][] TraceBoard;
	private PlayerStatsJson PlayerStatistics;
	private boolean IsWaitingToSecondPlayer;
	private boolean IsGameStartedNow;
	private boolean IsGameFinished = false;

	public BoardSigns[][] getGameBoard()
	{
		return GameBoard;
	}

	public void setGameBoard(BoardSigns[][] gameBoard)
	{
		GameBoard = gameBoard;
	}

	public BoardSigns[][] getTraceBoard()
	{
		return TraceBoard;
	}

	public void setTraceBoard(BoardSigns[][] traceBoard)
	{
		TraceBoard = traceBoard;
	}

	public JsonObjects.GameJson getGameJson()
	{
		return GameJson;
	}

	public void setGameJson(JsonObjects.GameJson gameJson)
	{
		GameJson = gameJson;
	}

	public void Set(Game i_Game, Player i_Player)
	{
		Gson gson = new Gson();
		GameJson gameJson = i_Game.GetGameAsJson();
		setGameJson(gameJson);
		setGameBoard(i_Player.GetBattleShipGameBoard().GetBoard());
		setTraceBoard(i_Player.GetTraceGameBoard().GetBoard());
	}

	public PlayerStatsJson getPlayerStatistics()
	{
		return PlayerStatistics;
	}

	public void setPlayerStatistics(PlayerStatsJson playerStatistics)
	{
		PlayerStatistics = playerStatistics;
	}

	public boolean isIsWaitingToSecondPlayer()
	{
		return IsWaitingToSecondPlayer;
	}

	public void setIsWaitingToSecondPlayer(boolean gameStarted)
	{
		IsWaitingToSecondPlayer = gameStarted;
	}

	public boolean isGameStartedNow()
	{
		return IsGameStartedNow;
	}

	public void setGameStartedNow(boolean gameStartedNow)
	{
		IsGameStartedNow = gameStartedNow;
	}

	public boolean isGameFinished()
	{
		return IsGameFinished;
	}

	public void setGameFinished(boolean gameFinished)
	{
		IsGameFinished = gameFinished;
	}
}
