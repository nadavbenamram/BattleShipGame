package Utils;

import BattleShipGameLogic.GameManager;
import BattleShipGameLogic.GameType;
import JsonObjects.GameJson;
import JsonObjects.GameStatisticsJson;

import javax.xml.ws.Dispatch;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game
{
	private String m_Title;
	private String m_XmlPath;
	private User m_Owner;
	private List<User> m_GameUsersList;
	private User[] m_CurrentGameUsers;
	private int m_CurrentPlayerIdx;
	private boolean m_IsActive;
	private GameManager m_GameManager;
	private boolean m_Started;
	private boolean m_IsUserLeft;
	private String m_LeftUser;

	public void SetUserLeft(String i_UserLeft)
	{
		m_IsUserLeft = true;
		m_LeftUser = i_UserLeft;
	}

	public String GetUserLeft()
	{
		return (m_IsUserLeft == true) ? m_LeftUser : "";
	}

	public Game(String i_Title)
	{
		m_Title = i_Title;
		m_IsActive = false;
		m_Started = false;
		m_IsUserLeft = false;
	}

	public int GetActivePlayersNum()
	{
		if(m_GameUsersList == null)
		{
			return 0;
		}
		else
		{
			return m_GameUsersList.size();
		}
	}

	public int GetCurrentPlayersNum()
	{
		if(m_GameUsersList == null)
		{
			return 0;
		}
		else
		{
			return m_GameUsersList.size();
		}
	}

	public User GetOwner()
	{
		return m_Owner;
	}

	public boolean IsActive()
	{
		return m_IsActive;
	}

	public void ActivateGame()
	{
		m_Started = true;
		m_IsActive = true;
	}

	public void DiAactivateGame()
	{
		m_IsActive = false;
	}

	public void SetOwner(User i_Owner)
	{
		m_Owner = i_Owner;
	}

	public GameManager GetGameManager()
	{
		return m_GameManager;
	}

	public void SetXmlPath(String i_XmlPath) throws Exception
	{
		if (i_XmlPath == null || i_XmlPath.equals(""))
		{
			throw new IllegalArgumentException("please choose xml file");
		}

		m_XmlPath = i_XmlPath;
		initGame();
	}

	private void initGame() throws Exception
	{
		if(m_XmlPath == null || m_XmlPath.equals(""))
		{
			throw new IllegalArgumentException("Please choose xml");
		}
		m_GameManager = new GameManager();
		m_GameManager.LoadGameSettings(m_XmlPath);
		m_GameManager.InitGame();
	}

	public void ResetGame() throws Exception
	{
		m_GameUsersList = null;
		m_Started = false;
		m_IsUserLeft = false;
		DiAactivateGame();
		initGame();
	}

	public String GetXmlPath()
	{
		return m_XmlPath;
	}

	public boolean AddUser(User i_User) throws Exception
	{
		boolean readyToStart = false;

		if(m_CurrentPlayerIdx >= Constants.PLAYERS_NUM_PER_NAME)
		{
			throw new Exception("Game has already " + Constants.PLAYERS_NUM_PER_NAME + " , this is the max number of players for one game.");
		}

		if(m_GameUsersList == null)
		{
			m_GameUsersList = new ArrayList(Constants.PLAYERS_NUM_PER_NAME);
			m_CurrentPlayerIdx = 0;
		}

		if(m_GameUsersList.contains(i_User))
		{
			throw new Exception("User " + i_User.GetName() + " already joined this game");
		}

		if(m_GameUsersList.size() == 0)
		{
			i_User.setPlayerIndex(0);
		}
		else
		{
			i_User.setPlayerIndex(1);
		}

		m_GameUsersList.add(i_User);

		if(m_GameUsersList.size() == Constants.NUM_OF_PLAYERS_PER_GAME)
		{
			ActivateGame();
			readyToStart = true;
		}

		return readyToStart;
	}

	public GameJson GetGameAsJson()
	{
		GameJson gameJson = new GameJson();
		GameStatisticsJson gameStatistics = new GameStatisticsJson();

		gameStatistics.setGameDuration(GetGameManager().GetGameStatistics().GetGameDuration());
		gameStatistics.setSteps(GetGameManager().GetGameStatistics().GetSteps());
		gameJson.setGameStatistcs(gameStatistics);
		gameJson.setTitle(GetTitle());
		gameJson.setOwner(GetOwner().GetName());
		gameJson.setBoardSize(GetGameManager().GetBoardSize());
		gameJson.setGameType(GetGameManager().GetGameType());
		gameJson.setActivePlayersNum(GetActivePlayersNum());
		gameJson.setCurrentPlayer(GetCurrentUser().GetUserAsJson());
		gameJson.setStarted(m_Started);

		return gameJson;
	}

	public boolean GetIsStarted()
	{
		return m_Started;
	}

	public void RemoveUser(User i_User) throws Exception
	{
		if(m_CurrentPlayerIdx <= 0)
		{
			throw new Exception("There aren't users in this game...");
		}

		try
		{
			m_GameUsersList.remove((i_User));
		}
		catch (Exception e)
		{
			throw new Exception("Can't find the user: " + i_User.GetName());
		}
	}

	public void StartGame()
	{
		m_CurrentGameUsers = (User[])m_GameUsersList.toArray();
	}

	public String GetTitle()
	{
		return m_Title;
	}

	public int GetUserIdx(User i_User)
	{
		if(i_User.GetName() == m_GameUsersList.get(0).GetName())
		{
			return 0;
		}
		else if(i_User.GetName() == m_GameUsersList.get(1).GetName())
		{
			return 1;
		}
		else
		{
			throw new IllegalArgumentException("This user isn't part of the game");
		}
	}

	public User GetCurrentUser()
	{
		return m_GameUsersList.get(m_CurrentPlayerIdx);
	}

	public void SetCurrentPlayerIdx(int i_Idx)
	{
		m_CurrentPlayerIdx = i_Idx;
	}

	public User GetUserByIdx(int i_Idx)
	{
		if (i_Idx == 0)
		{
			return m_GameUsersList.get(0);
		}
		else if (i_Idx == 1)
		{
			return m_GameUsersList.get(1);
		}
		else
		{
			throw new IllegalArgumentException("Player index isn't in the range 0-1");
		}
	}
}
