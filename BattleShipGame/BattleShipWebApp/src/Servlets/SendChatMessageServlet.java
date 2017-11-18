package Servlets;

		import Utils.*;

		import javax.naming.Context;
		import javax.servlet.ServletException;
		import javax.servlet.annotation.WebServlet;
		import javax.servlet.http.HttpServlet;
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;
		import javax.servlet.http.HttpSession;
		import java.io.IOException;
		import java.io.PrintWriter;

@WebServlet(name = "SendChatMessageServlet", urlPatterns = {"/sendmessage"})
public class SendChatMessageServlet extends HttpServlet
{
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ContextManager.SetContext(request.getServletContext());
		String gameTitle = (String) request.getParameter(Constants.GAME_TITLE_PARAM_NAME);
		String userName = (String) request.getParameter(Constants.USER_NAME_PARAM_NAME);
		String message = (String) request.getParameter(Constants.CHAT_MESSAGE_PARAM_NAME);
		PrintWriter writer = response.getWriter();
		try
		{
			Game game = ContextManager.Instance().GetGameByTitle(gameTitle);

			if(game == null)
			{
				writer.println("unkown game");
			}
			else if(userName == null || userName.equals(""))
			{
				writer.println("unkown user");
			}
			else if(message == null || message.equals(""))
			{
				writer.println("Game message");
			}
			else
			{
				game.AddMessage(userName, message);
				writer.println("YES");
			}
		}
		catch (IllegalArgumentException e)
		{
			writer.println(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleRequest(request, response);
	}
}
