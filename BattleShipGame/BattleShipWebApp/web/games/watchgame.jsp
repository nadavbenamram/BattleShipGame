<%@ page import="Utils.Constants" %>
<%@ page import="Utils.User" %>
<%@ page import="Utils.ContextManager" %>
<%@ page import="Utils.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String gameName = (String)request.getAttribute(Constants.GAME_TITLE_PARAM_NAME);
    Game game = ContextManager.Instance().GetGameByTitle(gameName);
%>
<head>
    <link rel="stylesheet" type="text/css" href="resources/style.css">
    <script type="text/javascript" src="resources/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="resources/watchgame.js"></script>
    <title>Watch Game Page</title>

    <style>
        #header {
            height: 10%;
            width: 100%;
            background-color: darkgray;
        }

        #leftSideBar {
            height: 80%;
            width: 20%;
            background-color: lightgray;
            float: left;
        }

        #mainContent {
            height: 80%;
            width: 60%;
            background-color: white;
            float: left;
        }

        #rightSideBar {
            height: 80%;
            width: 20%;
            background-color: lightgray;
            float: left;
        }

        #footer {
            height: 10%;
            width: 100%;
            background-color: darkgray;
            clear: both;
        }

        #GameDiv {
            float: left;
            margin-right: 10px;
            margin-left: 10px;
        }

        #TraceDiv {
            float: right;
            margin-right: 10px;
        }

        .title {
            text-align: center;
            vertical-align: middle;
            font-size:      20px;
        }

        table
        {
            border: 1px solid black;
        }

        table, th, td {
            border: 1px solid black;
        }

        label
        {
            color: darkblue;
        }

        p
        {
            color: darkgreen;
        }

        <!--
        @media (min-width:320px) and (max-width:480px) {
            #rightSideBar {
                float: none;
                width: 100%;
            }

            #mainContent {
                float: none;
                width: 80%;
                margin-left: 20%;
            }
        }
        -->
    </style>

</head>
<body>
<a type="hidden" gameName="<%=gameName%>" id="parametersFromJsp"></a>

<div id="header">
    <h1 align="center">Watch <%=gameName%> Game Page:</h1>
</div>

<div id="leftSideBar">
    <h1 align="center">Current Player Statistics:</h1>
    <label for="playerName">Player name: </label>
    <p id="playerName"></p>
    <label for="playerScore">Player score: </label>
    <p id="playerScore"></p>
    <label for="leaveGameButton">Back to game lists: </label>
    <input type="button" value="Leave game" id="leaveGameButton"></input>
</div>

<!-- Boards -->
<div id="mainContent">
    <div id="GameDiv" overflow-y:auto;>
        <p class="title">Game Board</p>
        <table id="GameTable">
        </table>
    </div>

    <div id="TraceDiv">
        <p class="title">Trace Board</p>
        <table id="TraceTable">
        </table>
    </div>
</div>

<div id="rightSideBar">
    <h1 align="center">Game Statistics:</h1>
    <label for="gameType">Game type: </label>
    <p id="gameType"></p>
    <label for="currentPlayer">Current player: </label>
    <p id="currentPlayer"></p>
    <label for="activePlayers">Active players at room: </label>
    <ul id="activePlayers">
        <li><%=game.GetUserByIdx(0) != null ? game.GetUserByIdx(0).GetName() : ""%></li>
        <li><%=game.GetUserByIdx(1) != null ? game.GetUserByIdx(1).GetName() : ""%></li>
    </ul>
    <label for="gameChat">Game chat: </label>
    <div id="gameChat" style="overflow-y:scroll; overflow-x:scroll; width: 100%; height: 200px; border-style: solid;"></div>
</div>

<div id="footer">
    <h1 align="center" id="statusLabel"></h1>
</div>

</body>
</html>
