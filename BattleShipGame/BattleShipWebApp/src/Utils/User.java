package Utils;

import JsonObjects.UserJson;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User
{
	private String m_Name;
	private List<Game> m_GamesList;
	Map<String, Integer> m_PlayerIndices;

	public User(String i_Name)
	{
		m_Name = i_Name;
		m_PlayerIndices = new HashMap<>();
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

	public int GetNumOfGames()
	{
		return m_GamesList == null ? 0 : m_GamesList.size();
	}

	public int getPlayerIndex(String name)
	{
		return m_PlayerIndices.get(name);
	}

	public void setPlayerIndex(String gameName, int playerIndex)
	{
		m_PlayerIndices.put(gameName, playerIndex);
	}

	public UserJson GetUserAsJson()
	{
		UserJson userJson = new UserJson();

		userJson.setName(m_Name);
		userJson.setNumOfGames(GetNumOfGames());

		return userJson;
	}
}
