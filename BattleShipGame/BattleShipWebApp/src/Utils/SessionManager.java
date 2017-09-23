package Utils;

import javax.servlet.http.HttpSession;

public class SessionManager
{
	private User m_CurrentUser;
	private static SessionManager m_Instance;
	private static HttpSession m_Session;

	private SessionManager()
	{
	}

	public static SessionManager Instance()
	{
		if(m_Instance == null)
		{
			m_Instance = new SessionManager();
		}

		return m_Instance;
	}

	public static void SetSession(HttpSession i_Session)
	{
		m_Session = i_Session;
	}

	public void SetCurrentUser(User i_User)
	{
		m_CurrentUser = i_User;
	}

	public User GetCurrentUser()
	{
		return m_CurrentUser;
	}
}
