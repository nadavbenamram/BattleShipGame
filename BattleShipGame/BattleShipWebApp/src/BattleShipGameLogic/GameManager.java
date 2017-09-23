package BattleShipGameLogic;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GameManager
{
	private generated.BattleShipGame m_BattleShipXmlObject;
	private GameType m_GameType;
	private int m_BoardSize;
	private static GameManager m_Instance;
	private Player[] m_Players;
	private final int NUM_OF_PLAYERS = 2;
	private GameStatistics m_Statistics;
	private int m_NextPlayerTurn;
	private ArrayList<AttackResult> m_GameHistory;
	private int m_CurrentGameHistory;

	public GameManager()
	{
	}

	public void SetGameHistoryIdx()
	{
		m_CurrentGameHistory = -1;
	}

	public AttackResult GetNextAttackHistory()
	{
		if(m_CurrentGameHistory > 0)
		{
			m_CurrentGameHistory--;
		}

		return m_GameHistory.get(m_CurrentGameHistory);
	}

	public AttackResult GetPrevAttackHistory()
	{
		if(m_CurrentGameHistory < m_GameHistory.size() - 1)
		{
			m_CurrentGameHistory++;
		}

		return m_GameHistory.get(m_CurrentGameHistory);
	}

	public static GameManager Instance()
	{
		if(m_Instance == null)
		{
			m_Instance = new GameManager();
		}

		return m_Instance;
	}

	public boolean IsAdvancedGameType()
	{
		return m_GameType == GameType.ADVANCE;
	}

	public int GetBoardSize()
	{
		return m_BoardSize;
	}

	public GameStatistics GetGameStatistics()
	{
		 return m_Statistics;
	}

	public void LoadGameSettings(String i_XmlPath) throws  Exception
	{
		fromXmlFileToObject(i_XmlPath);
		m_GameType = GameType.valueOf(m_BattleShipXmlObject.getGameType());
		m_BoardSize = m_BattleShipXmlObject.getBoardSize();
	}

	public String GetGameType()
	{
		return m_GameType.name();
	}

	public void InitGame()
	{
		initPlayers();
		m_Statistics = new GameStatistics();
		m_GameHistory = new ArrayList<>();
		m_CurrentGameHistory = 0;
		m_NextPlayerTurn = 0;
	}

	public Integer GetMaxNumOfMines()
	{
		//return m_BattleShipXmlObject.getMine() != null ? m_BattleShipXmlObject.getMine().getAmount() : null;
		return 2; //for Ex1 only. Upper line for Ex2.
	}

	private void fromXmlFileToObject(String i_FilePath) throws Exception
	{
		try {

			File file = new File(i_FilePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(generated.BattleShipGame.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			m_BattleShipXmlObject = (generated.BattleShipGame) jaxbUnmarshaller.unmarshal(file);

		}
		catch (JAXBException e)
		{
			throw new Exception("Couldn't load xml");
		}
	}

	public void SetMine(Player i_Player, Point i_Point) throws Exception
	{
		i_Player.SetMine(i_Point);
		AttackResult attack = new AttackResult(BoardSigns.EMPTY);
		attack.SetPlayers(GetAllPlayers(), i_Player.GetPlayerNumber());
		attack.AddAttackInfo("Player " + i_Player.GetPlayerNumber() + " set mine in (R: " + i_Point.y + ", C: " + (((char)('A' + i_Point.x - 1))) + ")");
		m_GameHistory.add(0, attack);

		m_NextPlayerTurn = nextPlayerIndex();
	}

	public AttackResult Attack(int i_AttackerIdx, int i_VictimIdx, Point i_Point, boolean i_IsMineAttack)
	{
		Player attacker = m_Players[i_AttackerIdx];
		Player victim = m_Players[i_VictimIdx];
		AttackResult attackResult;

		attackResult = victim.AttackedInPoint(i_Point);

		if(attackResult.GetBeforeAttackSign() != BoardSigns.BATTLE_SHIP && i_IsMineAttack == false)
		{
			m_NextPlayerTurn = nextPlayerIndex();
		}

		m_Players[m_NextPlayerTurn].TurnStarted();
		m_Statistics.AddStep();

		Player p = attacker.Clone();
		attackResult.SetPlayers(GetAllPlayers(), p.GetPlayerNumber());
		m_GameHistory.add(0, attackResult);

		return attackResult;
	}

	private int nextPlayerIndex()
	{
		return (m_NextPlayerTurn + 1) % NUM_OF_PLAYERS;
	}

	private void initPlayers()
	{
		m_Players = new Player[NUM_OF_PLAYERS];
		Player player;
		for(int i = 0; i < NUM_OF_PLAYERS; ++i)
		{
			player = new Player(i);
			player.SetBattleShipBoard(createPlayerBattleShipGameBoard(i));
			player.SetTraceBoard(createPlayerTraceGameBoard(i));
			m_Players[i] = player;
		}
	}

	private BattleShipGameBoard createPlayerBattleShipGameBoard(int i_PlayerIdx)
	{
		return new BattleShipGameBoard(m_BoardSize,
				                       m_BattleShipXmlObject.getBoards().getBoard().get(i_PlayerIdx),
				                       m_BattleShipXmlObject.getShipTypes(),
				                       m_BattleShipXmlObject.getMine());
	}

	private GameBoard createPlayerTraceGameBoard(int i_PlayerIdx)
	{
		return new GameBoard(m_BoardSize);
	}

	public Player GetCurrentPlayer()
	{
		return m_Players[m_NextPlayerTurn];
	}

	public Player[] GetAllPlayers()
	{
		return m_Players;
	}
}
