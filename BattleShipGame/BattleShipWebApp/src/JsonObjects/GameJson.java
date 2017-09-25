package JsonObjects;

public class GameJson
{
	private String Title;
	private String Owner;
	private int BoardSize;

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getOwner()
	{
		return Owner;
	}

	public void setOwner(String owner)
	{
		Owner = owner;
	}

	public int getBoardSize()
	{
		return BoardSize;
	}

	public void setBoardSize(int boardSize)
	{
		BoardSize = boardSize;
	}
}
