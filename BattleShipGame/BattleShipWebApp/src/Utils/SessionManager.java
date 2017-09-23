package Utils;

import javax.servlet.http.HttpSession;

public class SessionManager
{
	private static SessionManager m_Instance;
	private static HttpSession m_Session;

	private SessionManager()
	{
	}

	public static SessionManager Instance(HttpSession i_Session)
	{
		if(m_Instance == null)
		{
			m_Instance = new SessionManager();
		}

		m_Session = i_Session;

		return m_Instance;
	}

	public void SetCurrentUser(User i_User)
	{
		m_Session.setAttribute("user", i_User);
	}

	public User GetCurrentUser()
	{
		User user = (User)m_Session.getAttribute("user");
		return user;
	}
}
