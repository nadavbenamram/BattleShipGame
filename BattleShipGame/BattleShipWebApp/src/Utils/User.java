package Utils;

import java.util.ArrayList;
import java.util.List;

public class User
{
	private String m_Name;
	private List<Game> m_GamesList;

	public User(String i_Name)
	{
		m_Name = i_Name;
	}

	public void AddGame(Game i_Game)
	{
		if(m_GamesList == null)
		{
			m_GamesList = new ArrayList<Game>();
		}

		m_GamesList.add(i_Game);
	}

	public void RemoveGame(Game i_Game)
	{
		m_GamesList.remove(i_Game);
	}

	public String GetName()
	{
		return m_Name;
	}
}
