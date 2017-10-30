<%@ page import="JsonObjects.FinalGameStatistics" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../resources/style.css">
    <script type="text/javascript" src="../resources/jquery-3.2.1.min.js"></script>
    <title>Game finished</title>

    <style>
        #header {
            height: 10%;
            width: 100%;
            border: 1px solid black;
            background-color: darkgray;
        }

        #leftSideBar {
            height: 90%;
            width: 33%;
            border: 1px solid black;
            background-color: lightgray;
            float: left;
        }

        #mainContent {
            height: 90%;
            width: 33%;
            border: 1px solid black;
            background-color: lightgray;
            float: left;
        }

        #rightSideBar {
            height: 90%;
            width: 33%;
            border: 1px solid black;
            background-color: lightgray;
            float: left;
        }

        #returnToLobby
        {
            top: 10px;
            right: 10px;
        }

        label
        {
            color: darkblue;
            margin-left: 5px;
        }

        p
        {
            color: darkgreen;
            margin-left: 5px;
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
<%
    String gameTitle = (String)request.getAttribute("gameTitle");
    FinalGameStatistics gameStats = (FinalGameStatistics)request.getAttribute("gamestats");
    String title;
    if(gameStats.isPlayerLeft() == true)
    {
        title = "player " + gameStats.getPlayerLeftName() + " left the game and lose!";
    }
    else
    {
        if(gameStats.getPlayersStatistics()[0].getPoints() == gameStats.getPlayersStatistics()[1].getPoints())
        {
            title = "Draw!";
        }
        else if (gameStats.getPlayersStatistics()[0].getPoints() > gameStats.getPlayersStatistics()[1].getPoints())
        {
            title = "player " + gameStats.getPlayersStatistics()[1].getPlayerName() + " won!";
        }
        else
        {
            title = "player " + gameStats.getPlayersStatistics()[0].getPlayerName() + " won!";
        }
    }
%>

<div id="header">
    <h1 align="center" id="TitleMsg"><%=title%></h1>
    <input type="button" value="Return to lobby" id="returnToLobby"/>
</div>

<div id="leftSideBar">
    <h1 align="center">Game Statistics:</h1>
    <label for="TotalSteps">Total steps: </label>
    <p id="TotalSteps"><%=gameStats.getTotalSteps()%></p>
    <label for="GameDuration">Game duration: </label>
    <p id="GameDuration"><%=gameStats.getGameDuration()%></p>
</div>

<div id="mainContent">
    <h1 align="center">Player <%=gameStats.getPlayersStatistics()[0].getPlayerName()%> Statistics:</h1>
    <label for="PointsP1">Points: </label>
    <p id="PointsP1"><%=gameStats.getPlayersStatistics()[1].getPoints()%></p>
    <label for="MissP1">Miss: </label>
    <p id="MissP1"><%=gameStats.getPlayersStatistics()[0].getMiss()%></p>
    <label for="HitP1">Hit: </label>
    <p id="HitP1"><%=gameStats.getPlayersStatistics()[0].getHit()%></p>
    <label for="AverageStepTimeP1">Average step time: </label>
    <p id="AverageStepTimeP1"><%=gameStats.getPlayersStatistics()[0].getAverageStepTime()%></p>
</div>

<div id="rightSideBar">
    <h1 align="center">Player <%=gameStats.getPlayersStatistics()[1].getPlayerName()%> Statistics:</h1>
    <label for="PointsP2">Points: </label>
    <p id="PointsP2"><%=gameStats.getPlayersStatistics()[0].getPoints()%></p>
    <label for="MissP2">Miss: </label>
    <p id="MissP2"><%=gameStats.getPlayersStatistics()[1].getMiss()%></p>
    <label for="HitP2">Hit: </label>
    <p id="HitP2"><%=gameStats.getPlayersStatistics()[1].getHit()%></p>
    <label for="AverageStepTimeP2">Average step time: </label>
    <p id="AverageStepTimeP2"><%=gameStats.getPlayersStatistics()[1].getAverageStepTime()%></p>
</div>

<script type="text/javascript">
    (function(){
        $("#returnToLobby").click(function(){
            window.location.replace("../games");
        });
    })();
</script>

</body>
</html>
