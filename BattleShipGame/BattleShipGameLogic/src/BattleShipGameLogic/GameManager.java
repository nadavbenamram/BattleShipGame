package BattleShipGameLogic;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class GameManager
{
	private generated.BattleShipGame m_BattleShipXmlObject;
	private GameType m_GameType;
	private int m_BoardSize;
	private static GameManager m_Instance;
	private Player[] m_Players;
	private final int NUM_OF_PLAYERS = 2;
	private GameStatistics m_Statistics;

	private GameManager()
	{
	}

	public static GameManager Instance()
	{
		if(m_Instance == null)
		{
			m_Instance = new GameManager();
		}

		return m_Instance;
	}

	public void LoadGameSettings(String i_XmlPath) throws  Exception
	{
		fromXmlFileToObject(i_XmlPath);
		m_GameType = GameType.valueOf(m_BattleShipXmlObject.getGameType());
		m_BoardSize = m_BattleShipXmlObject.getBoardSize();
	}

	public void InitGame()
	{
		initPlayers();
		m_Statistics = new GameStatistics();
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
		return m_Players[m_Statistics.GetSteps() % NUM_OF_PLAYERS];
	}
}
