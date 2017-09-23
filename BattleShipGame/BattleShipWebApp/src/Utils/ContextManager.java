package Utils;

import com.sun.jndi.cosnaming.CNCtx;

import javax.servlet.ServletContext;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ContextManager
{
		private static ServletContext m_Context;
		private static ContextManager m_Instance = null;
		private List<User> m_ConnectedUsers;
		private List<Game> m_AllGames;
		private List<Game> m_ActiveGames;

		private ContextManager()
		{
		}

		public static void SetContext(ServletContext i_Context)
		{
			m_Context = i_Context;
		}

		public static ContextManager Instance()
		{
			if(m_Instance == null)
			{
				m_Instance = new ContextManager();
			}

			return  m_Instance;
		}

		public void AddUser(User i_User)
		{
			getConnectedUsers();
			boolean isUserExists = m_ConnectedUsers.stream().anyMatch(usr -> usr.GetName().equals(i_User.GetName()));
			if(true == isUserExists)
			{
				throw new IllegalArgumentException("There is already registerd user with the name" + i_User.GetName());
			}

			m_ConnectedUsers.add(i_User);
			m_Context.setAttribute(Constants.USERS_LIST_ATT_NAME, m_ConnectedUsers);
		}

		private void getConnectedUsers()
		{
			m_ConnectedUsers = (List<User>)m_Context.getAttribute(Constants.USERS_LIST_ATT_NAME);
			if(m_ConnectedUsers == null)
			{
				m_ConnectedUsers = new ArrayList<>();
				m_Context.setAttribute(Constants.USERS_LIST_ATT_NAME, m_ConnectedUsers);
			}
		}

	private void getAllGames()
	{
		m_AllGames = (List<Game>)m_Context.getAttribute(Constants.ALL_GAMES_ATT_NAME);
		if(m_AllGames == null)
		{
			m_AllGames = new ArrayList<>();
			m_Context.setAttribute(Constants.ALL_GAMES_ATT_NAME, m_AllGames);
		}
	}

		public void RemoveUser(User i_User) throws Exception
		{
			getConnectedUsers();

			try
			{
				m_ConnectedUsers.remove(i_User);
			}
			catch(Exception e)
			{
				throw new Exception("Can't find user " + i_User.GetName());
			}
		}

	public void AddGame(Game i_Game, User i_Owner)
	{
		getAllGames();

		boolean isUserExists = m_AllGames.stream().anyMatch(game -> game.GetTitle().equals(i_Game.GetTitle()));
		if(true == isUserExists)
		{
			throw new IllegalArgumentException("There is already registerd user with the name" + i_Game.GetTitle());
		}

		m_AllGames.add(i_Game);
		i_Owner.AddGame(i_Game);
		m_Context.setAttribute(Constants.ALL_GAMES_ATT_NAME, m_AllGames);
	}

	public void RemoveGame(Game i_Game)
	{
		getAllGames();

		m_AllGames.remove(i_Game);
		m_Context.setAttribute(Constants.ALL_GAMES_ATT_NAME, m_AllGames);
	}

	public List<Game> GetAllGames()
	{
		getAllGames();

		return m_AllGames;
	}

	public User GetUserByName(String userName)
	{
		List<User> users = m_ConnectedUsers.stream().filter(usr -> usr.GetName().equals(userName)).collect(Collectors.toList());
		return users.get(0);
	}

	public Game GetGameByTitle(String gameTitle)
	{
		List<Game> games = m_AllGames.stream().filter(game -> game.GetTitle().equals(gameTitle)).collect(Collectors.toList());
		return games.get(0);
	}
}
