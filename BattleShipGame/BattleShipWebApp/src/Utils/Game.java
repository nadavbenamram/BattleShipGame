package Utils;

import BattleShipGameLogic.GameType;

import java.util.ArrayList;
import java.util.List;

public class Game
{
	private String m_Title;
	private String m_XmlPath;
	private User m_Owner;
	private int m_BoardSize;
	private GameType m_GameType;
	private List<User> m_GameUsersList;
	private User[] m_CurrentGameUsers;
	private int m_CurrentPlayerIdx;
	private boolean m_IsActive;

	public Game(String i_Title)
	{
		m_Title = i_Title;
		m_IsActive = false;
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

	public void SetXmlPath(String i_XmlPath)
	{
		m_XmlPath = i_XmlPath;
	}

	public String GetXmlPath()
	{
		return m_XmlPath;
	}

	public void AddUser(User i_User) throws Exception
	{
		if(m_CurrentPlayerIdx >= Constants.PLAYERS_NUM_PER_NAME)
		{
			throw new Exception("Game has already " + Constants.PLAYERS_NUM_PER_NAME + " , this is the max number of players for one game.");
		}

		if(m_GameUsersList == null)
		{
			m_GameUsersList = new ArrayList(Constants.PLAYERS_NUM_PER_NAME);
			m_CurrentPlayerIdx = 0;
		}

		m_GameUsersList.add(i_User);
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
}
