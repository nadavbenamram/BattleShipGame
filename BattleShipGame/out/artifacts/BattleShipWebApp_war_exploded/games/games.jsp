<%@ page import="Utils.ContextManager" %>
<%@ page import="Utils.Game" %>
<%@ page import="java.util.List" %>
<%@ page import="Utils.Constants" %>
<%@ page import="Utils.SessionManager" %>
<%@ page import="static Utils.Constants.USER_NAME_PARAM_NAME" %>
<%@ page import="static Utils.Constants.GAME_TITLE_PARAM_NAME" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<HTML>

<HEAD>
    <link rel="stylesheet" type="text/css" href="../resources/style.css">
    <TITLE>Games Page</TITLE>
</HEAD>

<BODY >
<%
    String value = (String)request.getAttribute(Constants.GAME_LOAD_FAILED_ATT_NAME);
    if(value != null)
    {%>
<script type="text/javascript">
    alert("<%= value %>");
</script>
<%}%>

<h1>Games List:</h1>
<div id="gamesDiv">
    <table>
    <tr>
        <td align="center">Game Name</td>
        <td align="center">Game's owner</td>
        <td align="center">Board size</td>
        <td align="center">Game type</td>
        <td align="center">Ready for start</td>
    </tr>
        <%
        List<Game> games = ContextManager.Instance().GetAllGames();
        for (Game game: games) {
    %>
        <form method="post" action="../joingame">
            <tr>
                <td align="center"><%=game.GetTitle()%> </td>
                <td align="center"><%=game.GetOwner().GetName()%> </td>
                <td align="center"><%=game.GetGameManager().GetBoardSize()%></td>
                <td align="center"><%=game.GetGameManager().GetGameType()%></td>
                <td align="center"><%if (game.IsActive() == true){%>
                    <img src="../resources/red.png" alt="active game" style="width:15px;height:15px;">
                    <%}else if(game.GetActivePlayersNum() == 1){%>
                    <img src="../resources/yellow.png" alt="inactive game" style="width:15px;height:15px;">
                    <%}else{%>
                    <img src="../resources/green.jpg" alt="inactive game" style="width:15px;height:15px;">
                    <%}%>
                </td>
                <td><input type="hidden" name="<%= GAME_TITLE_PARAM_NAME%>" value="<%= game.GetTitle() %>" /></td>
                <td align="center"><%if (game.IsActive() == false){%><input type="submit" value="Join"><%}%></td>
            </tr>
        </form>
    <%}%>
    </table>
</div>

<hr />

<form method="post" action="../uploadgame" enctype="multipart/form-data">
    <label for="gameUpload">Upload game</label><br />
    <input type="text" name="gameTitle"/><br/><br/>
    <input type="file" accept=".xml" name="gameData" id="gameUpload"/><br/><br/>
    <input type="submit" value="Upload Game" />
</form>

<script type="text/javascript" src="../resources/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="games.js"/>

</BODY>

</HTML>