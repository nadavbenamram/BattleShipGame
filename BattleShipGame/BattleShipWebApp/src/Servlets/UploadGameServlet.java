package Servlets;

import Utils.*;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static Utils.Constants.GAME_LOAD_FAILED_ATT_NAME;

@MultipartConfig
@WebServlet(name = "UploadGameServlet", urlPatterns = {"/uploadgame"})
public class UploadGameServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String gameTItle = (String)request.getParameter("gameTitle"); // Retrieves <input type="file" name="file">
		Part filePart = request.getPart("gameData"); // Retrieves <input type="file" name="file">
		String xmlFolderPath = new File("").getAbsolutePath();
		xmlFolderPath += "/" + Constants.XML_FILES_DIR_NAME;
		File dir = new File(xmlFolderPath);
		dir.mkdir();
		File file = File.createTempFile(gameTItle + "_", ".xml", new File(xmlFolderPath));
		try (InputStream fileContent = filePart.getInputStream())
		{
			Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

		try
		{
			Game game = new Game(gameTItle);
			game.SetXmlPath(file.getAbsolutePath());
			User owner = SessionManager.Instance(request.getSession()).GetCurrentUser();
			game.SetOwner(owner);
			ContextManager.Instance().AddGame(game, owner);
		}
		catch (IllegalArgumentException e)
		{
			request.setAttribute(GAME_LOAD_FAILED_ATT_NAME, "There is already game with this title ("+gameTItle+")");
		}
		catch(Exception e)
		{
			request.setAttribute(GAME_LOAD_FAILED_ATT_NAME, e.getMessage());
		}
		finally
		{
			request.getRequestDispatcher("/games/games.jsp").forward(request,response);
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}
}
